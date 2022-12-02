package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.util.Command

class SetCommand(x: Int, y: Int, controller: Controller) extends Command {
  override def doStep: Unit = controller.grid = Grid(controller.gridSize, controller.grid.shots.addShot(x, y), controller.grid.ships)

  override def undoStep: Unit = controller.grid = Grid(controller.gridSize, controller.grid.shots.removeShot(), controller.grid.ships)

  override def redoStep: Unit = controller.grid = Grid(controller.gridSize, controller.grid.shots.addShot(x, y), controller.grid.ships)
}
