package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import de.htwg.se.battleship.model.state.{Player1State, Player2State, PlayerState}
import de.htwg.se.battleship.util.{Observable, UndoManager}


trait ControllerInterface extends Observable {
  val gridSize = 10
  val grid: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

  def changeState(): Unit

  def addShot(x: Int, y: Int): Unit

  def isSunk(index: Int): Boolean

  def isLost(): Boolean

  def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean

  def alreadyFired(x: Int, y: Int): Boolean

  def GridShipToString: String

  def set(x1: Int, y1: Int, x2: Int, y2: Int): Unit

  def undo(): Unit

  def redo(): Unit

  def autoShips(): Unit

  def isValid(input: String): Boolean

  def getX(input: String): Int

  def getY(input: String): Int

  def getBoard(): Vector[String]

  val player1: PlayerState = new Player1State(grid)
  val player2: PlayerState = new Player2State(grid)

  var state: PlayerState = player1

  val board: Vector[String] = Vector("A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "I2", "J2", "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "I3", "J3", "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "I4", "J4", "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "I6", "J6", "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "I10", "J10")



}
