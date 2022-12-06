package de.htwg.se.battleship
package aview

import scala.util.control.NonLocalReturns.*
import controller.*
import util.*

//noinspection ScalaWeakerAccess
class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def isValid(input: String): Boolean = input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

  def getX(input: String): Int = {

    val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

    if (char.matches("[a-j]")) {
      return char.charAt(0) - 'a' + 1
    }
    char.charAt(0) - 'A' + 1

  }

  def getY(input: String): Int = "(10)|([1-9])".r.findAllIn(input).mkString.toInt


  def addShotInput(input: String): Unit = {
    if (!this.isValid(input)) {
      println("Wrong input: " + input)
      println("Format example: <h6>\n")
    } else {
      val check = checkFired(input)
      if (check) {
        println("You already fired there!")
      } else {
        controller.addShot(this.getX(input), this.getY(input))
      }


    }

  }

  def checkFired(input: String): Boolean = returning {
    controller.state.grid.shots.X.indices.map(i =>
      if (controller.state.grid.shots.X(i) == this.getX(input) && controller.state.grid.shots.Y(i) == this.getY(input))
        throwReturn(true)
    )
    false
  }

  def removeShip(): Unit = controller.undo()

  //noinspection ScalaWeakerAccess
  def redoShip(): Unit = controller.redo()


  def addShipInput(start: String, ende: String): Unit = {

    if (!this.isValid(start) || !this.isValid(ende)) {
      println("Wrong input1")
      //println("Format example: <h6>\n")
    } else if (!controller.checkShip(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))) {
      println("Wrong Input2")
    } else controller.set(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))


    if (!controller.state.grid.ships.shipPosition()) {
      controller.undo()
      println("You already place a ship at this position!")
    }

    if (!controller.state.grid.ships.shipSingleCountValid()) {
      controller.undo()
      println("Ship is not valid anymore")
    }


  }

  def shipStartInput(line1: String): Unit = {

    line1 match
      case "undo" =>
        removeShip ()
        println ("Last Ship removed!")
      case "redo" =>
        redoShip()
        println("Last Ship redone")
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

  }

  override def update: Unit = println(controller.toString)
}
