package de.htwg.se.battleship.model

import de.htwg.se.battleship.controller.state.*

trait FileIOInterface {
  def save(state1: PlayerState, state2: PlayerState) : Unit
  def load() : Vector[PlayerState]

}
