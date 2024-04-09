package de.htwg.se.battleship.model

import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.state.*


trait FileIOInterface {
  def save(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState): Unit

  def load(): (PlayerState, PlayerState, Int, GameState)

}
