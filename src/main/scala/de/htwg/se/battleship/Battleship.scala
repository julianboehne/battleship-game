package de.htwg.se.battleship

import com.google.inject.{Guice, Injector}
import de.htwg.se.battleship.aview.TUI
import de.htwg.se.battleship.aview.gui.GUI
import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

object Battleship {


  val injector: Injector = Guice.createInjector(new BattleshipModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

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