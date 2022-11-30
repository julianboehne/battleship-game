package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class Grid(size: Int, shots: Shots, ships: ShipContainer) {

  def getGrid: String = {
    if (shots.X.isEmpty && shots.Y.isEmpty) return EmptyGrid(size).toString

    GridForShots(size, shots).toString


  }

  def getGridShips: String = {
    if (ships.shipsVector.isEmpty) return EmptyGrid(size).toString
    GridForShips(size,ships).toString
  }
  
}




