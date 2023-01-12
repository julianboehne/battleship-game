package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.gridImpl.*

trait GridInterface {

  val board: Vector[String]

  def getGridShots: String

  def getHit(i: Int): Boolean

  def getGridShips: String

  def getShots(): Shots

  def getSize(): Int

  def getShips(): ShipContainer

  def getBoard(): Vector[String]

  def getNumberSunk: Int

  def isSunk(index: Int): Boolean

}
