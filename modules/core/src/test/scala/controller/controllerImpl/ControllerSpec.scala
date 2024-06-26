package controller.controllerImpl

import akka.Done
import core.controller.controllerImpl.Controller
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import core.util.GameState
import core.util.state.{Player1State, Player2State}
import org.scalatest.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import util.state.*
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

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
    "set player name correctly" in {
      controller.setPlayerName("Test1")
      controller.player1.playerName.get shouldBe "Test1"
      controller.changeState()
      controller.setPlayerName("Test2")
      controller.player2.playerName.get shouldBe "Test2"
    }
    "reset the game correctly" in {
      controller.resetGame()
      controller.player1.playerName.get shouldBe "Player1"
      controller.player2.playerName.get shouldBe "Player2"
      controller.gameState shouldBe GameState.PLAYER_CREATE1
    }
    "auto place ships correctly" in {
      val result = Await.ready(controller.autoShips(), 10.seconds)
      assert(result.isCompleted)
    }
  }
}