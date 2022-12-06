package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.state.{Player1State, Player2State, PlayerState}
import de.htwg.se.battleship.util.{Observable, UndoManager}

class Controller(val grid1: Grid) extends Observable {

  private val undoManager = new UndoManager
  val gridSize = 10

  private val player1: PlayerState = new Player1State(grid1)
  private val player2: PlayerState = new Player2State(grid1)

  var state: PlayerState = player1

  def changeState(): Unit = {
    state match
      case _: Player1State => state = player2
      case _: Player2State => state = player1
  }


  def addShot(x: Int, y: Int): Unit = {
    state.grid = Grid(gridSize, state.grid.shots.addShot(x, y), state.grid.ships)

    notifyObservers
    //println("sunk: " + state.grid.ships.sunk(state.grid.ships.shipsVector(0), state.grid.shots))
  }


  def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = state.grid.ships.isValid(x1, y1, x2, y2)


  private def GridShipToString: String = state.grid.getGridShips


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
    println(GridShipToString)
    //notifyObservers
  }

  override def toString: String = state.grid.getGridShots


}
