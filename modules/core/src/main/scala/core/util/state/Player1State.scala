package core.util.state

//import controller.ControllerInterface
import core.model.GridInterface

class Player1State(var grid: GridInterface, name: String) extends PlayerState {

  override val playerName: Option[String] = {
    if (name.isEmpty) Some("Player1")
    else Some(name)
  }

  val board: Vector[String] = grid.board

  override def getBoard: Vector[String] = grid.getBoard

  override def getShipBoard: Vector[String] = grid.getShipBoard


}
