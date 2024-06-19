package de.htwg.se.battleship

import aview.*
import com.google.inject.{Guice, Injector}
import core.controller.ControllerInterface
import core.controller.controllerImpl.{APIController, Controller, KafkaConsumer}
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import gui.GUI

object Battleship {

  val grid: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  val controller = new Controller(grid)

  //---------------------------------------------
  // NOTE: Default setup (for Kafka setup comment out both lines:)
  val tui: TUI = TUI(controller)
  val gui = new GUI(controller)
  //---------------------------------------------

  //---------------------------------------------
  // NOTE: if you want to use Kafka instead comment in both lines:
  // val tui: TuiKafka = TuiKafka()
  // val consumer = new KafkaConsumer(controller)
  //---------------------------------------------


  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game\n")
    //---------------------------------------------
    // NOTE: if you want to use Kafka instead comment in this line:
    // consumer.start()
    //---------------------------------------------

    while (true) {
      tui.processInputLine()
      gui.update
    }

  }

}