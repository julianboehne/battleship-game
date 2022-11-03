package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.*
import aview.*

import scala.util.control.Breaks.break

object Battleship {

  def main(args: Array[String]): Unit = {

    val game = Game()
    game.startSetup()


    val input = TUI()
    while (true) {
      val line = readLine()
      if (!input.isValid(line)) {
        println("Fehler")
        break

      } else {
        game.setShot(input.getX(line), input.getY(line))
      }


    }


  }


}