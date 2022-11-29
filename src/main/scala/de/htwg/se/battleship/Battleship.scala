package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import model.*
import de.htwg.se.battleship.aview.TUI

object Battleship {

  def main(args: Array[String]): Unit = {
    val controller = new Controller(new Grid(10))
    val tui = new TUI(controller)




  }


}