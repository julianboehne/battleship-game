package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.aview.TUI
import scala.util.control.Breaks._

import scala.io.StdIn.readLine

object Battleship {
  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  val controller: Controller = Controller(grid)
  val tui: TUI = TUI(controller)

  def main(args: Array[String]): Unit = {
    print("Welcome to Battleship-Game\n")

    while (controller.grid.ships.shipCountValid()) {
      print("Startwert (oder rm/redo/exit): ")

      val line1 = readLine()
      if (line1 == "exit" || line1 == "1") return
      else tui.shipStartInput(line1)
      
      if (controller.grid.ships.shipCountValid()) {
        val line2 = readLine()
        if (line2 == "exit" || line2 == "1") return
        else tui.addShipInput(line1, line2)
      }

      

    }


    while (true) {
      print("Shot(ex. H5): ")
      //Eingabe
      val line = readLine()
      //Exit
      if (line == "exit" || line == "1") {
        return
      }
      //controller.addShot(tui.getX(line), tui.getY(line))
      tui.addShotInput(line)

    }


  }


}