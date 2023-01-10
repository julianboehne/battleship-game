package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.gridImpl.GridTemplate

class EmptyGrid(size: Int) extends GridTemplate {
  def fullField: String = field(width, size)

  override def toString: String = field(width, size)
}
