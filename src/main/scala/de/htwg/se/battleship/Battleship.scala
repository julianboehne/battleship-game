package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.aview.TUI

import scala.io.StdIn.readLine

object Battleship {
  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()),ShipContainer(Vector[Ship]()))
  val controller: Controller = Controller(grid)
  val tui: TUI = TUI(controller)

  def main(args: Array[String]): Unit = {
    print("Welcome to Battleship-Game\n")
//
//    tui.addShipInput("a1", "a5")
//    tui.addShipInput("c1", "c3")
//    tui.addShipInput("g10", "j10")
//    tui.addShipInput("f3", "f4")



    while (true) {
      print("Shot(ex. H5): ")
      //Eingabe
      val line = readLine()
      //Exit
      if (line == "exit" || line == "1") {
        return
      }
      tui.addShotInput(line)

    }




  }


}