package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.*
import aview.*


object Battleship {
  val game: Game = Game()
  val input: TUI = TUI()

  def main(args: Array[String]): Unit = {

    game.startSetup()

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
        game.setShot(input.getX(line), input.getY(line))

      }


    }


  }


}