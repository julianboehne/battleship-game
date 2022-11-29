package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.Grid
import de.htwg.se.battleship.util.Observable

class Controller(var grid: Grid) extends Observable{
    def createEmptyGrid(size: Int): Unit = {
      grid = new Grid(size) 
      notifyObservers
    }

}
