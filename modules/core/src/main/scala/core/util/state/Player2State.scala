package core.util.state

import core.model.GridInterface

class Player2State(var grid: GridInterface, name: String) extends PlayerState {

  override val playerName: Option[String] = {
    if (name.isEmpty) Some("Player2")
    else Some(name)
  }

  val board: Vector[String] = grid.board

  override def getBoard: Vector[String] = grid.getBoard

  override def getShipBoard: Vector[String] = grid.getShipBoard


}
