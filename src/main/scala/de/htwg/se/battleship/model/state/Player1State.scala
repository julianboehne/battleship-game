package de.htwg.se.battleship.model.state

import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.model.GridInterface
import de.htwg.se.battleship.model.gridImpl.Grid

class Player1State(var grid: GridInterface, controller: ControllerInterface) extends PlayerState {

  override val playerName: String = "Player1"

  val board: Vector[String] = grid.board

  override def getBoard(): Vector[String] = grid.getBoard()


}
