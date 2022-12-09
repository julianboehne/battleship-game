package de.htwg.se.battleship
package aview

import scala.util.control.NonLocalReturns.*
import controller.*
import util.*

import scala.util.Try

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  var shipStart: String = ""

  def processInputLine(input: String): Unit = {

    if (controller.state.grid.ships.shipCountValid()) {
      if (shipStart.equals("")) {
        shipStartInput(input)
      } else {
        addShipInput(shipStart, input)
        shipStart = ""
      }

      if (!controller.state.grid.ships.shipCountValid() && controller.state == controller.player1) {
        println("change")
        controller.changeState()
      }

    } else {
      if (addShotInput(input) == 0) controller.changeState()


    }


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
        //println("Format example: <h6>\n")
      } else if (!controller.checkShip(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))) {
        println("Wrong Input2")
      } else controller.set(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))
    )
    if (e.isFailure) println("invalid")
    else {
      if (!controller.state.grid.ships.shipPosition()) {
        controller.undo()
        println("You already place a ship at this position!")
      }

      if (!controller.state.grid.ships.shipSingleCountValid()) {
        controller.undo()
        println("Ship is not valid anymore")
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
            println(controller.state.playerName)
        case _ => print("Endwert: ")

    )
    if (e.isFailure) println("Exception")
    else shipStart = line1


  }

  override def update: Unit = println(controller.toString)
}
