package de.htwg.se.battleship
package aview

import scala.util.control.NonLocalReturns.*
import controller.*
import util.*

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

      controller.grid.shots.X.indices.map(i =>
        if (controller.grid.shots.X(i) == this.getX(input) && controller.grid.shots.Y(i) == this.getY(input))
          println("You already fired there!")
          return
      )

      controller.addShot(this.getX(input), this.getY(input))
    }

  }

  def removeShip(): Unit = controller.undo()

  def redoShip(): Unit = controller.redo()


  def addShipInput(start: String, ende: String): Unit = {

    if (!this.isValid(start) || !this.isValid(ende)) {
      println("Wrong input1")
      //println("Format example: <h6>\n")
    } else if (!controller.checkShip(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))) {
      println("Wrong Input2")
    } else controller.set(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))


    if (!controller.grid.ships.shipPosition()) {
      controller.undo()
      println("You already place a ship at this position!")
    }

    if (!controller.grid.ships.shipSingleCountValid()) {
      controller.undo()
      println("Ship is not valid anymore")
    }


  }

  def shipStartInput(line1: String): Unit = {

    if (line1 == "undo") {
      removeShip()
      println("Last Ship removed!")
    } else if (line1 == "redo") {
      redoShip()
      println("Last Ship redone")

    } else if (line1 == "auto") { //später weg
      addShipInput("a1", "a2")
      addShipInput("c1", "c2")
      addShipInput("j1", "i1")

      addShipInput("a7", "a9")
      addShipInput("b5", "b3") //b5, b3 geht nicht

      addShipInput("d6", "g6")
      addShipInput("j3", "j6")

      addShipInput("f1", "f5")
    } else {
      print("Endwert: ")
    }
  }

  override def update: Unit = println(controller.toString)
}
