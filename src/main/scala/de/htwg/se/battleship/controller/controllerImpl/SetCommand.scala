package de.htwg.se.battleship.controller.controllerImpl

import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.gridImpl.Grid
import de.htwg.se.battleship.util.Command

class SetCommand(x1: Int, y1: Int, x2: Int, y2: Int,  controller: ControllerInterface) extends Command {
  override def doStep: Unit = controller.state.grid = Grid(controller.grid.getSize(), controller.state.grid.getShots(), controller.state.grid.getShips().addShip(controller.state.grid.getShips().getShip(x1,y1, x2, y2)))

  override def undoStep: Unit = controller.state.grid = Grid(controller.grid.getSize(), controller.state.grid.getShots() , controller.state.grid.getShips().removeShip())

  override def redoStep: Unit = controller.state.grid = Grid(controller.grid.getSize(), controller.state.grid.getShots(), controller.state.grid.getShips().addShip(controller.state.grid.getShips().getShip(x1,y1, x2, y2)))


}
