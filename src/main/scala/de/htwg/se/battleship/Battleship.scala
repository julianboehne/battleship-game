package de.htwg.se.battleship

import aview.TUI
import com.google.inject.{Guice, Injector}
import core.controller.ControllerInterface
import core.controller.controllerImpl.Controller
import core.controller.controllerImpl.APIController
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import gui.GUI

object Battleship {


//  val injector: Injector = Guice.createInjector(new BattleshipModule)
//  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  val controller = new Controller(grid)

  val tui: TUI = TUI(controller)
  val gui = new GUI(controller)

  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")

    while (true) {
      tui.processInputLine()
      gui.update
    }

  }

}