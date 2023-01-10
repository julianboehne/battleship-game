package de.htwg.se.battleship.model.state

import de.htwg.se.battleship.model.GridInterface
import de.htwg.se.battleship.model.gridImpl.Grid

trait PlayerState {

  val playerName: String

  val board: Vector[String]

  var grid: GridInterface

  def getBoard(): Vector[String]

}
