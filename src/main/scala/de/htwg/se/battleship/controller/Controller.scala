package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.util.{Observable, UndoManager}

class Controller(var grid: Grid) extends Observable {

  private val undoManager = new UndoManager
  val gridSize = 10

  //  def createEmptyGrid(): Unit = {
  //    grid = new Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
  //    notifyObservers
  //  }


  def addShot(x: Int, y: Int): Unit = {
    grid = Grid(gridSize, grid.shots.addShot(x, y), grid.ships)

    notifyObservers
  }


  def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = grid.ships.isValid(x1, y1, x2, y2)

  /*  def addShip(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
      //prüfen
      val ship: Ship = grid.ships.getShip(x1, y1, x2, y2)
      grid = Grid(gridSize, grid.shots, grid.ships.addShip(ship))
      notifyObservers

    }*/

  def GridShipToString: String = grid.getGridShips


  def set(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    undoManager.doStep(new SetCommand(x1, y1, x2, y2, this))
    println(GridShipToString)
    //notifyObservers
  }

  def undo(): Unit = {
    undoManager.undoStep
    println(GridShipToString)
    //notifyObservers
  }

  def redo(): Unit = {
    undoManager.redoStep
    //notifyObservers
  }

  override def toString: String = grid.getGridShots


}
