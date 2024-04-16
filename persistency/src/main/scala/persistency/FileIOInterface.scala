package persistency

import util.state.*
import util.GameState.*

trait FileIOInterface {
  def save(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState): Unit

  def load(): (PlayerState, PlayerState, Int, GameState)

}
