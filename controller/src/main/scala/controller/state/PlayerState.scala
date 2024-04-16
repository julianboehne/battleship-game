package controller.state

trait PlayerState {

  val playerName: Option[String]

  val board: Vector[String]

  var grid: GridInterface

  def getPlayerName: String

  def getBoard: Vector[String]

  def getShipBoard: Vector[String]

}
