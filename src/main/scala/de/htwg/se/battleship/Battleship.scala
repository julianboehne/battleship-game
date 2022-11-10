package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.*
import aview.*
import controller.*


object Battleship {
  val controller: Controller = Controller(FieldView())
  val input: TUI = TUI()

  def main(args: Array[String]): Unit = {

    controller.gameSetup()

    while (true) {
      print(s"Shot(ex. H5): ${Console.CYAN}")
      val line = readLine()
      println()

      if (line == "exit") {
        return
      }

      if (!input.isValid(line)) {
        println(s"${Console.RESET}Wrong input:${Console.RED} $line")
        println(s"${Console.YELLOW}Format example: <h6>\n ${Console.RESET}")

      } else {
        print(s"${Console.RESET}")
        controller.addShot(input.getX(line), input.getY(line))

      }


    }


  }


}