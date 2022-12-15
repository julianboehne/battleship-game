package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.gridImpl.*

trait GridInterface {

  def getGridShots: String

  def getHit(i: Int): Boolean

  def getGridShips: String

  def getShots(): Shots

  def getSize(): Int

  def getShips(): ShipContainer

}
