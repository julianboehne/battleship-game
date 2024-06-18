package de.htwg.se.battleship

import aview.*
import com.google.inject.{Guice, Injector}
import core.controller.ControllerInterface
import core.controller.controllerImpl.{APIController, Controller, KafkaConsumer}
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import gui.GUI

object Battleship {


//  val injector: Injector = Guice.createInjector(new BattleshipModule)
//  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  val controller = new Controller(grid)

//  val tui: TUI = TUI(controller)
  val tui: TuiKafka = TuiKafka()
  val consumer = new KafkaConsumer(controller)
  val gui = new GUI(controller)

  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")
    consumer.start()

    while (true) {
      tui.processInputLine()
      gui.update
    }

  }

}