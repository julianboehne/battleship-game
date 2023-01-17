package de.htwg.se.battleship.model

import de.htwg.se.battleship.controller.state.*

trait FileIOInterface {
  def save(state1: Player1State, state2: Player2State) : Unit
  def load() : PlayerState

}
