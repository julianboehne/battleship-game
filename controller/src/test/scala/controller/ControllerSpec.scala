package controller

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import util.GameState

//TODO



import org.scalatest.*

class ControllerSpec extends AnyWordSpec {
  val nextline: String = sys.props("line.separator")

  "Controller" should  {
    val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

    val controller = new Controller(grid)

    "checks if ship is valid" in {
      assert(controller.checkShip(1, 2, 1, 3))
    }
    "checks if shot is already fired" in {
      controller.addShot(1, 1)
      assert(controller.alreadyFired(1, 1))
    }
    "checks if the player has lost the game" in {
      assert(controller.isLost)
    }
    "checks if input is valid" in {
      assert(controller.isValid("a1"))
    }
    "gets x value from input" in {
      assert(controller.getX("a1") == 1)
      assert(controller.getX("A1") == 1)
    }
    "gets y value from input" in {
      assert(controller.getY("a1") == 1)
    }
    "change the state of the game" in {
      controller.changeState()
      assert(controller.state.isInstanceOf[Player2State])
      controller.changeState()
      assert(controller.state.isInstanceOf[Player1State])
    }
    "return the GameState message" in {
      controller.gameState = GameState.END
      assert(controller.GameStateText == "Thank you for playing Battleship-Game")
    }
    "return the GridShips" in {
      assert(controller.GridShipToString == "+----+" + nextline + "|    |" + nextline + "+----+" + nextline)
    }
    "return the GridShots" in {
      assert(controller.toString == "+----+" + nextline + "|  0 |" + nextline + "+----+" + nextline)
    }

  }
}