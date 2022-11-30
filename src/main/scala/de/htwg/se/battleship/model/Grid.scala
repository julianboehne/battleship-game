package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class Grid(size: Int, shots: Shots) {

  def getGrid: String = {
    if (shots.X.isEmpty && shots.Y.isEmpty) return new EmptyGrid(size).toString

    new GridPerShot(size, shots).toString
  }
  
}




