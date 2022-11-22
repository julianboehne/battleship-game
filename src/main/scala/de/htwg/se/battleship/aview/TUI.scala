package de.htwg.se.battleship
package aview

import controller.*
import de.htwg.se.battleship.model.Field
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
      val success = controller.addShot(this.getX(line), this.getY(line), controller.getField())
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

  override def update: Unit = println(controller.toString)
}
