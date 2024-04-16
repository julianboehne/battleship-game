package controller.state

import controller.ControllerInterface

class Player1State(var grid: GridInterface, name: String) extends PlayerState {

  override val playerName: Option[String] = {
    if (name.isEmpty) None
    else Some(name)
  }

  val board: Vector[String] = grid.board

  override def getPlayerName: String = {
    playerName match
      case Some(s) => s
      case None => "Player1"

  }

  override def getBoard: Vector[String] = grid.getBoard

  override def getShipBoard: Vector[String] = grid.getShipBoard


}
