package de.htwg.se.battleship.controller.state

import de.htwg.se.battleship.model.GridInterface
import de.htwg.se.battleship.model.gridImpl.Grid

trait PlayerState {

  val playerName: Option[String]

  val board: Vector[String]

  var grid: GridInterface

  def getPlayerName: String

  def getBoard(): Vector[String]

  def getShipBoard(): Vector[String]

}
