package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.state.{Player1State, Player2State, PlayerState}
import de.htwg.se.battleship.util.{Observable, UndoManager}
import scala.util.control.NonLocalReturns.*


  class Controller(val grid1: Grid) extends Observable {
  
    private val undoManager = new UndoManager
    val gridSize = 10
  
    val player1: PlayerState = new Player1State(grid1)
    val player2: PlayerState = new Player2State(grid1)
  
    var state: PlayerState = player1
    
    def changeState(): Unit = {
      state match
        case _: Player1State => state = player2
        case _: Player2State => state = player1
    }
  
  
    def addShot(x: Int, y: Int): Unit = {
      state.grid = Grid(gridSize, state.grid.shots.addShot(x, y), state.grid.ships)
      notifyObservers
    }
  
    def isSunk(index: Int): Boolean = returning {
      (0 until state.grid.ships.shipsVector(index).size).foreach(i =>
        if (!state.grid.shots.wasShot(state.grid.ships.shipsVector(index).getX(i), state.grid.ships.shipsVector(index).getY(i))) throwReturn(false)
      )
      true
  
    }
  
    def isLost(): Boolean = returning {
      state.grid.ships.shipsVector.indices.foreach(i =>
        if (!isSunk(i)) throwReturn(false)
      )
      true
    }
    
    def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = state.grid.ships.isValid(x1, y1, x2, y2)
    
    def alreadyFired(x: Int, y:Int): Boolean = returning {
      state.grid.shots.X.indices.foreach(i =>
        if (state.grid.shots.X(i) == x && state.grid.shots.Y(i) == y)
          throwReturn(true)
      )
      false
    }
  
    def GridShipToString: String = state.grid.getGridShips
  
  
    def set(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
      undoManager.doStep(new SetCommand(x1, y1, x2, y2, this))
      println(GridShipToString)
      //gui Schiffausgabe
      //notifyObservers
    }
  
    def undo(): Unit = {
      undoManager.undoStep
      println(GridShipToString)
      //notifyObservers
    }
  
    def redo(): Unit = {
      undoManager.redoStep
      println(GridShipToString)
      //notifyObservers
    }
    def autoShips(): Unit = {
      this.set(1,1,1,2)
      this.set(3,1,3,2)
      this.set(10,1,9,1)

      this.set(1,7,1,9)
      this.set(2,5,2,3)

      this.set(4,6,7,6)
      this.set(10,3,10,6)

      this.set(6,1,6,5)
    }

    def isValid(input: String): Boolean = input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

    def getX(input: String): Int = {

      val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

      if (char.matches("[a-j]")) {
        return char.charAt(0) - 'a' + 1
      }
      char.charAt(0) - 'A' + 1

    }

    def getY(input: String): Int = "(10)|([1-9])".r.findAllIn(input).mkString.toInt
  
    override def toString: String = state.grid.getGridShots
  
  
  }
