package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.util.Command

class SetCommand(x1: Int, y1: Int, x2: Int, y2: Int,  controller: Controller) extends Command {
  override def doStep: Unit = controller.state.grid = Grid(controller.gridSize, controller.state.grid.shots, controller.state.grid.ships.addShip(controller.state.grid.ships.getShip(x1,y1, x2, y2)))

  override def undoStep: Unit = controller.state.grid = Grid(controller.gridSize, controller.state.grid.shots , controller.state.grid.ships.removeShip())

  override def redoStep: Unit = controller.state.grid = Grid(controller.gridSize, controller.state.grid.shots, controller.state.grid.ships.addShip(controller.state.grid.ships.getShip(x1,y1, x2, y2)))

}
