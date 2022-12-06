package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.aview.TUI
import de.htwg.se.battleship.model.state.Player1State

import scala.io.StdIn.readLine

object Battleship {
  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  val controller: Controller = Controller(grid)
  val tui: TUI = TUI(controller)

  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")

    while (controller.state.grid.ships.shipCountValid()) {
      println(controller.state.playerName)
      print("Startwert (oder undo/redo/exit): ")

      val line1 = readLine()
      if (line1 == "exit" || line1 == "1") return
      else tui.shipStartInput(line1)

      if (controller.state.grid.ships.shipCountValid() && line1 != "redo" && line1 != "undo") {
        val line2 = readLine()
        if (line2 == "exit" || line2 == "1") return
        else tui.addShipInput(line1, line2)
      }

      //if (!controller.state.grid.ships.shipCountValid() && controller.state == controller.player1) controller.changeState()

      if (!controller.state.grid.ships.shipCountValid()) {
        controller.state match
          case _: Player1State => controller.changeState()
          case _ =>
      }

    }

    //controller.changeState()


    while (true) {
      println(controller.state.playerShotName)
      print("Shot(ex. H5): ")
      //Eingabe
      val line = readLine()
      //Exit
      if (line == "exit" || line == "1") {
        return
      }

      tui.addShotInput(line)
      controller.changeState()

    }

  }

}