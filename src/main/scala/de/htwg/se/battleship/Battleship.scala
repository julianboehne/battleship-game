package de.htwg.se.battleship

import de.htwg.se.battleship.aview.TUI
import de.htwg.se.battleship.aview.gui.GUI
import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

import scala.io.StdIn.readLine
import com.google.inject.Guice

object Battleship {


  val injector = Guice.createInjector(new BattleshipModule)
  val controller = injector.getInstance(classOf[ControllerInterface])

  val tui: TUI = TUI(controller)
  val gui = new GUI(controller)


  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")
    //println(controller.GridShipToString)
    var input: String = ""

    while (true) {
      //println(controller.state.playerName)
      input = readLine()
      if (input.equals("q")) System.exit(0)
      tui.processInputLine(input)
      gui.update
    }


  }

}