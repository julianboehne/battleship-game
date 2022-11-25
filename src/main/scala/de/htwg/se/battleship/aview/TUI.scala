package de.htwg.se.battleship
package aview

import controller.*
import de.htwg.se.battleship.model.Field
import de.htwg.se.battleship.model.Player
import util.*


class TUI(controller: Controller) extends Observer {

  controller.add(this)

  def setup(): Int = {
    print(controller.gameSetup())
    0
  }

  def run(line: String): Int = {
    println()
    if (!this.isValid(line)) {
      println(s"${Console.RESET}Wrong input:${Console.RED} $line")
      println(s"${Console.GREEN}Format example: <h6>\n ${Console.RESET}")
      return 1

    } else {
      print(s"${Console.WHITE}")
      val success = controller.addShot(this.getX(line), this.getY(line), controller.getField)
      print(s"${Console.RESET}")
      if (success == 1) {
        println(s"${Console.RED}You already fired there!${Console.RESET}")
      } else controller.setField()
    }
    0

  }
  
  def isValid(input: String): Boolean = {
    input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

  }

  def getX(input: String): Int = {

    val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

    if (char.matches("[a-j]")) {
      return char.charAt(0)-'a'+1
    }
    char.charAt(0)-'A'+1

  }


  def getY(input: String): Int = {

    val num = "(10)|([1-9])".r.findAllIn(input).mkString.toInt

    num
  }

  def shipCountPrint(): Int = {
    
    print(s"${Console.BLUE}Ships:${Console.RESET} \n")

    print(s"Ship 2: ${controller.getField.ships.shipTwoCount}, ")
    print(s"Ship 3: ${controller.getField.ships.shipThreeCount}, ")
    print(s"Ship 4: ${controller.getField.ships.shipFourCount}, ")
    print(s"Ship 5: ${controller.getField.ships.shipFiveCount}\n")
    0
  }
  def addShip(startpunkt: String, endpunkt: String): Int ={
    if (!isValid(startpunkt) || !isValid(endpunkt)) {
      println("not valid1")
      return 1
    }
    val valid = controller.getField.addNewShip(getX(startpunkt), getY(startpunkt), getX(endpunkt), getY(endpunkt))
    if (valid == 1) {
      println("not valid2")
      return 1
    }
    0
  }


  override def update: Unit = println(controller.toString)
}
