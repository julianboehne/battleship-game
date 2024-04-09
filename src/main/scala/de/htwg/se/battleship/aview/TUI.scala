package de.htwg.se.battleship.aview

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import scala.io.StdIn.readLine
import scala.util.*

class TUI(controller: ControllerInterface) extends Observer {
  controller.add(this)

  private var shipStart: Option[String] = None
  var input: Option[String] = None

  def processInputLine(): Unit = {
    println(controller.GameStateText)
    if (controller.gameState == END) println(s"${controller.state.getPlayerName} has won the game!")

    //Eingabe
    input = Some(readLine())
    input match {
      case Some("q") => System.exit(0)
      case Some("save") => controller.saveGame()
      case Some("load") => controller.loadGame()
      case Some("new") => controller.resetGame()
      case _ =>
    }

    controller.gameState match {
      case PLAYER_CREATE1 | PLAYER_CREATE2 => addPlayer(input.get)
      case SHIP_PLAYER1 | SHIP_PLAYER2 =>
        shipStart match {
          case None => shipStart = Some(input.get)
          case Some(start) =>
            addShipInput(start, input.get)
            shipStart = None
        }
      case SHOTS =>
        if (addShotInput(input.get) == 0 && controller.isLost) controller.gameState = END
      case END => println("end")
    }
  }

  private def addPlayer(input: String): Unit = {
    controller.setPlayerName(input)
    controller.gameState match {
      case PLAYER_CREATE1 =>
        controller.state = controller.player2
        controller.gameState = PLAYER_CREATE2
      case PLAYER_CREATE2 =>
        controller.state = controller.player1
        controller.gameState = SHIP_PLAYER1
    }
  }

  def addShotInput(input: String): Int = {
    if (!controller.isValid(input)) {
      println(s"Wrong input: $input\nFormat example: <h6>")
      1
    } else if (controller.alreadyFired(controller.getX(input), controller.getY(input))) {
      println("You already fired there!")
      1
    } else {
      controller.addShot(controller.getX(input), controller.getY(input))
      0
    }
  }

  private def addShipInput(start: String, end: String): Unit = {
    if (!controller.isValid(start) || !controller.isValid(end) || !controller.checkShip(controller.getX(start), controller.getY(start), controller.getX(end), controller.getY(end))) {
      println("Wrong input")
    } else {
      controller.set(controller.getX(start), controller.getY(start), controller.getX(end), controller.getY(end))
      if (!controller.state.grid.ships.shipCountValid()) {
        controller.gameState match {
          case SHIP_PLAYER1 =>
            controller.state = controller.player2
            controller.gameState = SHIP_PLAYER2
          case SHIP_PLAYER2 =>
            controller.state = controller.player1
            controller.gameState = SHOTS
        }
      }
    }
  }

  override def update: Unit = println(controller.toString)
}