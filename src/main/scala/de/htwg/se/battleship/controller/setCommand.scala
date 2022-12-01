package de.htwg.se.battleship.controller

import de.htwg.se.battleship.util.Command

class SetCommand(row: Int, col: Int, value: Int, controller: Controller) extends Command {
  override def doStep: Unit = controller.grid = controller.grid

  override def undoStep: Unit = controller.grid = controller.grid

  override def redoStep: Unit = controller.grid = controller.grid
}
