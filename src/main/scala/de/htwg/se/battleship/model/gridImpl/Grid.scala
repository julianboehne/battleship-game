package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.*


case class Grid(size: Int, shots: Shots, ships: ShipContainer) extends GridInterface {

  override def getGridShots: String = {
    if (shots.X.isEmpty && shots.Y.isEmpty) return EmptyGrid(size).toString
    GridForShots(size, this).toString
  }

  override def getHit(i: Int): Boolean =
    ships.isHit(shots.getX(i), shots.getY(i))


  override def getGridShips: String = {
    if (ships.shipsVector.isEmpty) return EmptyGrid(size).toString
    GridForShips(size,ships).toString
  }

  override def getShots(): Shots = shots

  override def getSize(): Int = size

  override def getShips(): ShipContainer = ships


  
}






