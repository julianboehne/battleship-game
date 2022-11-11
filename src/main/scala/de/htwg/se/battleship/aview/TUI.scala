package de.htwg.se.battleship
package aview

import controller.*
import util.Observer

import scala.io.StdIn.readLine


class TUI(controller: Controller) extends Observer {

  controller.add(this)

  def run(): Int = {
    controller.gameSetup()
    this.loop()
    0
  }

  def loop(): Int = {
    print(s"Shot(ex. H5): ${Console.CYAN}")
    val line = readLine()
    println()

    if (line == "exit") {
      return 0
    }

    if (!this.isValid(line)) {
      println(s"${Console.RESET}Wrong input:${Console.RED} $line")
      println(s"${Console.YELLOW}Format example: <h6>\n ${Console.RESET}")

    } else {
      print(s"${Console.RESET}")
      controller.addShot(this.getX(line), this.getY(line))
      update

    }
    this.loop()
    1
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

  override def update: Unit = println("Huso")
}
