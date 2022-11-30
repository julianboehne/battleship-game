package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.util.Observable

class Controller(var grid: Grid) extends Observable{
  val gridSize = 4
    def createEmptyGrid(): Unit = {
      //grid = new EmptyGrid(gridSize)
      grid = new Grid(gridSize, Shots(Vector[Int](),Vector[Int]()))
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
    grid = new Grid(gridSize, grid.shots.addShot(x, y))
    notifyObservers
  }






}
