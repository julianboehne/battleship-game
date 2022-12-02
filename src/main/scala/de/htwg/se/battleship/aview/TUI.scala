package de.htwg.se.battleship
package aview

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
    }

//    (0 to controller.grid.shots.X.size-1).map(i =>
//      if(controller.grid.shots.X(i) == this.getX(input) && controller.grid.shots.Y(i) == this.getX(input)) {
//        //controller.undo(this.getX(input),this.getY(input))
//        println("You already fired there!")
//        return
//      }
//    )

    controller.set(this.getX(input), this.getY(input))

  }
  def removeShot(): Unit = controller.undo()

  def redoShot(): Unit = controller.redo()


  def addShipInput(start: String, ende: String): Unit = {

    if (!this.isValid(start) || !this.isValid(ende)) {
      println("Wrong input")
      //println("Format example: <h6>\n")
    } else if (!controller.checkShip(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))) {
        println("Wrong Input")
    } else {
      controller.addShip(this.getX(start), this.getY(start), this.getX(ende), this.getY(ende))
    }

  }


  override def update: Unit = println(controller.toString)
}
