package controller

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.model.*
import com.google.inject.{Guice, Injector}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.Try

object ControllerAPI {

  val injector: Injector = Guice.createInjector(new BattleshipModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "BattleshipHTTPServer")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val route = concat(
      path("controller" / "changeState") {
        get {
          controller.changeState()
          complete(StatusCodes.OK, s"Changed state: ${controller.state}")
        }
      },
      path("controller" / "addShot") {
        post {
          formFields(
            "x".as[Int],
            "y".as[Int],
          ) { (x, y) =>
            try {
              controller.addShot(x, y)
              complete(StatusCodes.OK, s"Added shot successfully")
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "islost") {
        get {
          complete(StatusCodes.OK, controller.isLost.toString)
        }
      },
      path("controller" / "checkship") {
        post {
          formFields(
            "x1".as[Int],
            "y1".as[Int],
            "x2".as[Int],
            "y2".as[Int],
          ) { (x1, y1, x2, y2) =>
            try {
              complete(StatusCodes.OK, controller.checkShip(x1, y1, x2, y2).toString)
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "alreadyfired") {
        post {
          formFields(
            "x".as[Int],
            "y".as[Int],
          ) { (x, y) =>
            try {
              complete(StatusCodes.OK, controller.alreadyFired(x, y).toString)
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "shipgridstring") {
        get {
          complete(StatusCodes.OK, controller.GridShipToString)
        }
      },
      path("controller" / "set") {
        post {
          formFields(
            "x1".as[Int],
            "y1".as[Int],
            "x2".as[Int],
            "y2".as[Int],
          ) { (x1, y1, x2, y2) =>
            try {
              controller.set(x1, y1, x2, y2)
              complete(StatusCodes.OK, s"Set ${x1} ${y1} ${x2} ${y2}")
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "undo") {
        get {
          controller.undo()
          complete(StatusCodes.OK,"Undo")
        }
      },
      path("controller" / "redo") {
        get {
          controller.redo()
          complete(StatusCodes.OK, "Redo")
        }
      },
      path("controller" / "autoships") {
        get {
          controller.autoShips()
          complete(StatusCodes.OK, "Auto deploy ships")
        }
      },
      path("controller" / "isvalid") {
        post {
          formFields(
            "input".as[String],
          ) { (input) =>
            try {
              complete(StatusCodes.OK, controller.isValid(input).toString)
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "xvalue") {
        post {
          formFields(
            "input".as[String],
          ) { (input) =>
            try {
              complete(StatusCodes.OK, controller.getX(input).toString)
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "yvalue") {
        post {
          formFields(
            "input".as[String],
          ) { (input) =>
            try {
              complete(StatusCodes.OK, controller.getY(input).toString)
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "setplayername") {
        post {
          formFields(
            "name".as[String],
          ) { (name) =>
            try {
              controller.setPlayerName(name)
              complete(StatusCodes.OK, s"Set player name to ${name}")
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
      path("controller" / "gamestate") {
        get {
          complete(StatusCodes.OK, controller.GameStateText)
        }
      },
      path("controller" / "reset") {
        get {
          controller.resetGame()
          complete(StatusCodes.OK, "Reset game")
        }
      },
      path("controller" / "reset") {
        get {
          controller.saveGame()
          complete(StatusCodes.OK, "Save game")
        }
      },
      path("controller" / "reset") {
        get {
          controller.loadGame()
          complete(StatusCodes.OK, "Load game")
        }
      },
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // Lässt den Server laufen, bis der Benutzer Return drückt
    bindingFuture
      .flatMap(_.unbind()) // Löst die Bindung vom Port
      .onComplete(_ => system.terminate())

  }

}
