package de.htwg.se.battleship.aview

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import scala.io.StdIn.readLine
import scala.util.*
import scala.util.control.NonLocalReturns.*

class TUI(controller: ControllerInterface) extends Observer {
  controller.add(this)

  var shipStart: String = ""
  var input: String = ""

  def processInputLine(): Unit = {

    println(controller.GameStateText)

    if (controller.gameState == END) {
      println(controller.state.getPlayerName + " has won the game!")
    }
    //Eingabe
    input = readLine()

    input match {
      case "q" => System.exit(0)
      case "save" =>
        controller.saveGame()
        return
      case "load" =>
        controller.loadGame()
        return
      case "new" =>
        controller.resetGame()
        return
      case _ =>
    }


    controller.gameState match
      case PLAYER_CREATE1 => addPlayer(input)
      case PLAYER_CREATE2 => addPlayer(input)
      case SHIP_PLAYER1 =>
        if (shipStart.equals("")) {
          shipStartInput(input)
        } else {
          addShipInput(shipStart, input)
          shipStart = ""
        }
      case SHIP_PLAYER2 =>
        if (shipStart.equals("")) {
          shipStartInput(input)
        } else {
          addShipInput(shipStart, input)
          shipStart = ""
        }
      case SHOTS =>

        if (addShotInput(input) == 0) {

          if (controller.isLost()) {
            controller.gameState = END
          }
          if (!controller.state.grid.getShips().isHit(controller.state.grid.getShots().getLatestX, controller.state.grid.getShots().getLatestY)) {
            controller.changeState()
          } else println("Hit! You can fire again.")

        }

      case END => println("end")

  }

  def addPlayer(input: String): Unit = {
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


  def addShotInput(input: String): Int = {
    if (!controller.isValid(input)) {
      println("Wrong input: " + input)
      println("Format example: <h6>\n")
      1
    } else {
      val check = checkFired(input)
      if (check) {
        println("You already fired there!")
        1
      } else {
        controller.addShot(controller.getX(input), controller.getY(input))
        0
      }


    }

  }

  def checkFired(input: String): Boolean = controller.alreadyFired(controller.getX(input), controller.getY(input))


  def removeShip(): Unit = controller.undo()

  def redoShip(): Unit = controller.redo()


  def addShipInput(start: String, ende: String): Unit = {

    val e = Try(

      if (!controller.isValid(start) || !controller.isValid(ende)) {
        println("Wrong input1")
      } else if (!controller.checkShip(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))) {
        println("Wrong Input2")
      } else {
        controller.set(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))
        //GameState
        if (!controller.state.grid.getShips().shipCountValid()) {
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
      if (!controller.state.grid.getShips().shipPosition()) {
        controller.undo()
        println("You already place a ship at this position!")
      }

      if (!controller.state.grid.getShips().shipSingleCountValid()) {
        controller.undo()
        println("Too many ships with this size")
      }
    }



  }

  def shipStartInput(line1: String): Unit = {

    val e = Try(

      line1 match
        case "undo" =>
          removeShip()
          println ("Last Ship removed!")
        case "redo" =>
          redoShip()
          println ("Last Ship redone")
        case "auto" =>
          controller.autoShips()
          println ("Auto ship placement")
        case _ =>
          shipStart = line1
            println ("Second value: ")

    )
    if (e.isFailure) println("Exception")

    if (line1.equals("auto")) {
      controller.gameState match
        case SHIP_PLAYER1 =>
          controller.state = controller.player2
          controller.gameState = SHIP_PLAYER2
        case SHIP_PLAYER2 =>
          controller.state = controller.player1
          controller.gameState = SHOTS
    }

  }

  override def update: Unit = {
    println(controller.toString)
  }
}
