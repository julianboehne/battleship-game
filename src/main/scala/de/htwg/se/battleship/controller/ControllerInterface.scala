package de.htwg.se.battleship.controller

import de.htwg.se.battleship.controller.GameState.GameState
import de.htwg.se.battleship.controller.state.{Player1State, Player2State, PlayerState}
import de.htwg.se.battleship.model.GridInterface
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import de.htwg.se.battleship.util.{Observable, UndoManager}


trait ControllerInterface extends Observable {
  val grid: GridInterface
  var gameState: GameState
  var player1: PlayerState
  var player2: PlayerState
  var state: PlayerState

  def changeState(): Unit
  def addShot(x: Int, y: Int): Unit
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
  def setPlayerName(name: String): Unit
  def GameStateText:String
  def resetGame(): Unit








}
