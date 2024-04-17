package controller.controllerImpl

import controller.ControllerInterface
import model.gridImpl.Grid
import util.Command

class SetCommand(x1: Int, y1: Int, x2: Int, y2: Int, controller: ControllerInterface) extends Command {
  override def doStep: Unit = {
    controller.state.grid.ships.addShipSafely(controller.state.grid.ships.getShip(x1, y1, x2, y2)) match {
      case Right(newShips) => controller.state.grid = Grid(controller.grid.size, controller.state.grid.shots, newShips)
      case Left(errorMessage) => println(errorMessage)
    }
  }

  override def undoStep: Unit = controller.state.grid = Grid(controller.grid.size, controller.state.grid.shots, controller.state.grid.ships.removeShip())

  override def redoStep: Unit = controller.state.grid = Grid(controller.grid.size, controller.state.grid.shots, controller.state.grid.ships.addShip(controller.state.grid.ships.getShip(x1, y1, x2, y2)))


}
