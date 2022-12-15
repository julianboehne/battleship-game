package de.htwg.se.battleship

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.aview.gui.GUI
import de.htwg.se.battleship.aview.tuiImpl.TUI
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import de.htwg.se.battleship.model.state.Player1State

import scala.io.StdIn.readLine

object Battleship {
  
  val controller: Controller = Controller()
  val tui: TUI = TUI(controller)
  val gui = new GUI(controller)


  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")


    var input: String = ""

    while (true) {
      println(controller.state.playerName)
      input = readLine()
      if (input.equals("q")) return
      tui.processInputLine(input)
      gui.update
    }

/*    while
      input = readLine()
      tui.processInputLine(input)
      input != "1"
      //!controller.isLost()
    do ()*/







    /*while (controller.state.grid.ships.shipCountValid()) {
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
      if (controller.isLost()) {
        println("The winner is " + controller.state.playerShotName)
        println("Thanks for playing")
        return
      }
      controller.changeState()

    }*/

  }

}