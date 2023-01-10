package de.htwg.se.battleship.aview

import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import scala.util.*
import scala.util.control.NonLocalReturns.*

class TUI(controller: ControllerInterface) extends Observer {
  controller.add(this)

  private var shipStart: String = ""

  def processInputLine(input: String): Unit = {

    if (controller.state.grid.getShips().shipCountValid()) {
      if (shipStart.equals("")) {
        shipStartInput(input)
      } else {
        addShipInput(shipStart, input)
        shipStart = ""
      }


      if (!controller.state.grid.getShips().shipCountValid()){
        controller.changeState()
        println("Player change")
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
      if (!controller.state.grid.getShips().shipPosition()) {
        controller.undo()
        println("You already place a ship at this position!")
      }

      if (!controller.state.grid.getShips().shipSingleCountValid()) {
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
        case _ =>
          shipStart = line1
            println ("Endwert: ")

    )
    if (e.isFailure) println("Exception")


  }

  override def update: Unit = println(controller.toString)
}
