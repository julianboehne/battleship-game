package controller.controllerImpl

import core.controller.controllerImpl.{Controller, SetCommand}
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.*

class SetCommandSpec extends AnyWordSpec {
  "A SetCommand" when {
    "doStep is called" should {
      "add a ship to the grid" in {
        val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
        val controller = new Controller(grid)
        val command = new SetCommand(1, 1, 1, 3, controller)
        command.doStep
        assert(controller.state.grid.ships.getSize == 1)
      }
      "fail to add a wrong ship" in {
        val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
        val controller = new Controller(grid)
        val command = new SetCommand(40, 20, 1, 3, controller)
        command.doStep
        assert(controller.state.grid.ships.getSize == 0)
      }
    }
    "undoStep is called" should {
      "remove the last added ship from the grid" in {
        val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
        val controller = new Controller(grid)
        val command = new SetCommand(1, 1, 1, 3, controller)
        command.doStep
        command.undoStep
        assert(controller.state.grid.ships.getSize == 0)
      }
    }
    "redoStep is called" should {
      "add the last removed ship back to the grid" in {
        val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
        val controller = new Controller(grid)
        val command = new SetCommand(1, 1, 1, 3, controller)
        command.doStep
        command.undoStep
        command.redoStep
        assert(controller.state.grid.ships.getSize == 1)
      }
    }
  }
}