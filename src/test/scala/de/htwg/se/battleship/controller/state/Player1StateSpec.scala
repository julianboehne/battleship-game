package de.htwg.se.battleship.controller.state
import de.htwg.se.battleship.Battleship.injector
import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
class Player1StateSpec extends AnyWordSpec {

  val grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

  val player1State1 = new Player1State(grid,  "Test")
  val player1State2 = new Player1State(grid,  "")

  "A Player1State" should {

    "return the correct player name" in {
      player1State1.getPlayerName should be("Test")
      player1State2.getPlayerName should be("Player1")
    }

    "return the correct board" in {
      player1State1.getBoard() should be(Vector("A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "I2", "J2", "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "I3", "J3", "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "I4", "J4", "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "I6", "J6", "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "I10", "J10"))
    }
    "should have a val board" in {
      player1State1.board should be (player1State1.getBoard())
    }
  }
}
