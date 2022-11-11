package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.FieldView
import aview.*
import controller.*


object Battleship {
  val controller: Controller = Controller(FieldView(4, 10))
  val input: TUI = TUI(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game")

    input.run()

  }


}