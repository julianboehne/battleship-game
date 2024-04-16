package controller

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GameStateSpec extends AnyWordSpec {
  val gamestate: GameState.type = GameState
  "GameState" should {
    "message function" in {
      gamestate.message(GameState.PLAYER_CREATE1) should be("Please enter your name")
      gamestate.message(GameState.PLAYER_CREATE2) should be("Please enter your name")
      gamestate.message(GameState.SHIP_PLAYER1) should be("Please enter your ships")
      gamestate.message(GameState.SHIP_PLAYER2) should be("Please enter your ships")
      gamestate.message(GameState.SHOTS) should be("Please enter your Shot")
      gamestate.message(GameState.END) should be("Thank you for playing Battleship-Game")
    }
    "Map the Game State correctly" in {
      gamestate.determineGameState("PLAYER_CREATE1") should be(GameState.PLAYER_CREATE1)
      gamestate.determineGameState("PLAYER_CREATE2") should be(GameState.PLAYER_CREATE2)
      gamestate.determineGameState("SHIP_PLAYER1") should be(GameState.SHIP_PLAYER1)
      gamestate.determineGameState("SHIP_PLAYER2") should be(GameState.SHIP_PLAYER2)
      gamestate.determineGameState("SHOTS") should be(GameState.SHOTS)
      gamestate.determineGameState("END") should be(GameState.END)

      val exception = intercept[IllegalArgumentException] {
        gamestate.determineGameState("TestError")
      }
      exception.getMessage should be("Invalid game state: TestError")
    }
  }
}
