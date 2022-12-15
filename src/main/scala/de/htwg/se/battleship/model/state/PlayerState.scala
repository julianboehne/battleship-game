package de.htwg.se.battleship.model.state

import de.htwg.se.battleship.model.gridImpl.Grid

trait PlayerState {

  val playerName: String

  val playerShotName: String

  var grid: Grid

}
