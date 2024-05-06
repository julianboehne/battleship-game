package core.util.state

import core.model.GridInterface

trait PlayerState {

  val playerName: Option[String]

  val board: Vector[String]

  var grid: GridInterface
  
  def getBoard: Vector[String]

  def getShipBoard: Vector[String]

}
