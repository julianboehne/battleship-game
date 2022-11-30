package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.aview.TUI

object Battleship {

  def main(args: Array[String]): Unit = {
    val grid: Grid = Grid(10, Shots(Vector[Int](),Vector[Int]()))
    val controller: Controller = Controller(grid)
    val tui: TUI = TUI(controller)




  }


}