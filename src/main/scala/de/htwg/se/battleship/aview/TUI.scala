package de.htwg.se.battleship
package aview

import scala.util.control.NonLocalReturns.*
import controller.*
import util.*

import scala.util.Try

class TUI(controller: Controller) extends Observer {
  controller.add(this)


  def addShotInput(input: String): Unit = {
    if (!controller.isValid(input)) {
      println("Wrong input: " + input)
      println("Format example: <h6>\n")
    } else {
      val check = checkFired(input)
      if (check) {
        println("You already fired there!")
      } else {
        controller.addShot(controller.getX(input), controller.getY(input))
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
          addShipInput("a1", "a2")
            addShipInput("c1", "c2")
            addShipInput("j1", "i1")

            addShipInput("a7", "a9")
            addShipInput("b5", "b3")

            addShipInput("d6", "g6")
            addShipInput("j3", "j6")

            addShipInput("f1", "f5")
        case "auto2" =>
          addShipInput("a2", "a3")
            addShipInput("c1", "c2")
            addShipInput("j1", "i1")

            addShipInput("a7", "a9")
            addShipInput("b5", "b3")

            addShipInput("d6", "g6")
            addShipInput("j3", "j6")

            addShipInput("f1", "f5")
        case _ => print("Endwert: ")

    )
    if (e.isFailure) println("Exception")


  }

  override def update: Unit = println(controller.toString)
}
