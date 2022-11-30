package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.util.Observable

class Controller(var grid: Grid) extends Observable {
  val gridSize = 10

  def createEmptyGrid(): Unit = {
    //grid = new EmptyGrid(gridSize)
    grid = new Grid(gridSize, Shots(Vector[Int](), Vector[Int]()),ShipContainer(Vector[Ship]()))
    notifyObservers
  }

  def createGridWithShots(): Unit = {
    println("moin")
    notifyObservers
  }

  def createGridWithShips(): Unit = {
    println("test")
    notifyObservers

  }

  def addShot(x: Int, y: Int): Unit = {
    grid = Grid(gridSize, grid.shots.addShot(x, y), ShipContainer(Vector[Ship]()))
    notifyObservers
  }

  def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = grid.ships.isValid(x1, y1, x2, y2)

  def addShip(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    //prüfen
    val ship: Ship = grid.ships.getShip(x1: Int, y1: Int, x2: Int, y2: Int)
    grid = Grid(gridSize, grid.shots ,grid.ships.addShip(ship))
    notifyObservers

  }

  override def toString: String = grid.getGridShips


}
