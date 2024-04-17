package model

import model.gridImpl.*

trait GridInterface {

  val board: Vector[String]
  val shots: Shots
  val size: Int
  val ships: ShipContainer

  def getGridShots: String

  def getHit(i: Int): Boolean

  def getGridShips: String

  def getBoard: Vector[String]

  def getShipBoard: Vector[String]

  def getNumberSunk: Int

  def isSunk(index: Int): Boolean

}
