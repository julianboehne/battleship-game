package de.htwg.se.battleship.model.state

import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.model.gridImpl.Grid

class Player2State(var grid: Grid, controller: ControllerInterface) extends PlayerState {

  override val playerName: String = "Player2"

  val board: Vector[String] = grid.board

  override def getBoard(): Vector[String] = grid.getBoard()


}
