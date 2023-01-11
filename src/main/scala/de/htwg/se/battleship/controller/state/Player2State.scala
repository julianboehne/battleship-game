package de.htwg.se.battleship.controller.state

import de.htwg.se.battleship.controller.ControllerInterface
import de.htwg.se.battleship.model.GridInterface
import de.htwg.se.battleship.model.gridImpl.Grid

class Player2State(var grid: GridInterface, controller: ControllerInterface, name: String) extends PlayerState {

  override val playerName: Option[String] = {
    if(name.isEmpty) None
    else Some(name)
  }

  val board: Vector[String] = grid.board

  override def getPlayerName: String = {
    playerName match
      case Some(s) => s
      case None => "Player2"

  }

  override def getBoard(): Vector[String] = grid.getBoard()


}
