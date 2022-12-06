package de.htwg.se.battleship.model.state
import de.htwg.se.battleship.model.Grid

class Player1State(var grid: Grid) extends PlayerState {

  override val playerName: String = "Player1"

  override val playerShotName: String = "Player2"


}
