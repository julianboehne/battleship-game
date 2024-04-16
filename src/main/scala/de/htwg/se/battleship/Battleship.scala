package de.htwg.se.battleship

import aview.TUI
import com.google.inject.{Guice, Injector}
import controller.{BattleshipModule, ControllerInterface}
import gui.GUI

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