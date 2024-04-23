package aview

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.*
import com.google.inject.{Guice, Injector}
import controller.{BattleshipModule, ControllerInterface}
import util.GameState.*
import util.state.{Player1State, PlayerState, Player2State}

import util.*

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.Try

object ServiceAPI {

  val injector: Injector = Guice.createInjector(new BattleshipModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "BattleshipHTTPServer")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val route = concat(
      path("battleship" / "gamestate") {
        get {
          complete(StatusCodes.OK, s"Game State: ${controller.gameState} \n\n${controller.GameStateText}")
        }
      },
      path("battleship" / "options") {
        post {
          formFields(
            "option".as[String]
          ) { (option) =>
          option match {
            case "save" =>
              controller.saveGame()
              complete(StatusCodes.OK, "Game saved")
            case "load" =>
              controller.loadGame()
              complete(StatusCodes.OK, "Game loaded")
            case "new" =>
              controller.resetGame()
              complete(StatusCodes.OK, "Started new game")
            case _ => complete(StatusCodes.BadRequest, "No valid option given")
          }
        }
          }
      },
      path("battleship" / "playername") {
        post {
          formFields(
            "playerName".as[String]
          ) { (playerName) =>
            try {
              if (controller.gameState != GameState.PLAYER_CREATE1 || controller.gameState != GameState.PLAYER_CREATE2) {
                complete(StatusCodes.BadRequest, s"Wrong Game State, actual state: ${controller.GameStateText}")
              } else {
                addPlayer(playerName)
                complete(StatusCodes.OK, s"Player name set to: ${playerName}\nNew state: ${controller.GameStateText}")
              }
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("battleship" / "addship") {
        post {
          formFields(
            "coordinate1".as[String],
            "coordinate2".as[String],
          ) { (coordinate1, coordinate2) =>
            try {
              if (controller.gameState != GameState.SHIP_PLAYER1 || controller.gameState != GameState.SHIP_PLAYER2) {
                complete(StatusCodes.BadRequest, s"Wrong Game State, actual state: ${controller.GameStateText}")
              } else {
                addShipInput(coordinate1, coordinate2)
                complete(StatusCodes.OK, s"Added ship from ${coordinate1} to ${coordinate2}\nNew state: ${controller.GameStateText}")
              }
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("battleship" / "addship" / "options") {
        post {
          formFields(
            "option".as[String],
          ) { (option) =>
            try {
              if (controller.gameState != GameState.SHIP_PLAYER1 || controller.gameState != GameState.SHIP_PLAYER2) {
                complete(StatusCodes.BadRequest, s"Wrong Game State, actual state: ${controller.GameStateText}")
              } else {
                option match
                  case "undo" =>
                    controller.undo()
                    complete(StatusCodes.OK, "Last Ship removed!")
                  case "redo" =>
                    controller.redo()
                    complete(StatusCodes.OK, "Last Ship redone")
                  case "auto" =>
                    controller.autoShips()
                    complete(StatusCodes.OK, "Auto ship placement")
                  case _ => complete(StatusCodes.BadRequest, "No valid option given")
              }
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("battleship" / "shot") {
        post {
          formFields(
            "shot".as[String],
          ) { (shot) =>
            try {
              if (controller.gameState != GameState.SHOTS) {
                complete(StatusCodes.BadRequest, s"Wrong Game State, actual state: ${controller.GameStateText}")
              } else {
                addShotInput(shot)
                if (controller.isLost) {
                  controller.gameState = END
                  complete(StatusCodes.OK, s"${controller.state.playerName} lost!\nNew state: ${controller.GameStateText}")
                }
                if (!controller.state.grid.ships.isHit(controller.state.grid.shots.getLatestX.getOrElse(0), controller.state.grid.shots.getLatestY.getOrElse(0))) {
                  controller.changeState()
                } else complete(StatusCodes.OK, s"Shot: ${shot}\nHit! You can fire again: ${controller.state.getPlayerName}")
                complete(StatusCodes.OK, s"Shot: ${shot}\nActual player: ${controller.state.playerName}")
              }
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      }//shot
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // Lässt den Server laufen, bis der Benutzer Return drückt
    bindingFuture
      .flatMap(_.unbind()) // Löst die Bindung vom Port
      .onComplete(_ => system.terminate())

  }

  private def addPlayer(input: String): Unit = {
    controller.gameState match
      case PLAYER_CREATE1 =>
        controller.state = controller.player1
        controller.setPlayerName(input)
        controller.state = controller.player2
        controller.gameState = PLAYER_CREATE2
      case PLAYER_CREATE2 =>
        controller.state = controller.player2
        controller.setPlayerName(input)
        controller.state = controller.player1
        controller.gameState = SHIP_PLAYER1
  }


  private def addShipInput(start: String, ende: String): Unit = {

    val e = Try(

      if (!controller.isValid(start) || !controller.isValid(ende)) {
        complete(StatusCodes.BadRequest, "Wrong coordinates")
      } else if (!controller.checkShip(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))) {
        complete(StatusCodes.BadRequest, "Wrong coordinates")
      } else {
        controller.set(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))
        //GameState
        if (!controller.state.grid.ships.shipCountValid()) {
          controller.gameState match
            case SHIP_PLAYER1 =>
              controller.state = controller.player2
              controller.gameState = SHIP_PLAYER2
            case SHIP_PLAYER2 =>
              controller.state = controller.player1
              controller.gameState = SHOTS
        }
      }
    )
    if (e.isFailure) println("invalid")
    else {
      if (!controller.state.grid.ships.shipPosition()) {
        controller.undo()
        complete(StatusCodes.BadRequest, "You already place a ship at this position!")
      }

      if (!controller.state.grid.ships.shipSingleCountValid()) {
        controller.undo()
        complete(StatusCodes.BadRequest, "Too many ships with this size")
      }
    }

  }

  def addShotInput(input: String): Unit = {
    if (!controller.isValid(input)) {
      complete(StatusCodes.BadRequest, s"Wrong input: ${input} \nFormat example: h6")
    } else {
      val check = controller.alreadyFired(controller.getX(input), controller.getY(input))
      if (check) {
        complete(StatusCodes.BadRequest, "You already fired there!")
      } else {
        controller.addShot(controller.getX(input), controller.getY(input))
      }
    }

  }

}
