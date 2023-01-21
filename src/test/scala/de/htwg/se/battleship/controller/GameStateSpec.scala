package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.gridImpl.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
  }
}
