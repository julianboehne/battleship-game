package core.controller

import core.model.GridInterface
import core.util.GameState.GameState
import core.util.{Observable, UndoManager}
import core.util.state.{Player1State, Player2State, PlayerState}


trait ControllerInterface extends Observable {
  val grid: GridInterface
  var gameState: GameState
  var player1: PlayerState
  var player2: PlayerState
  var state: PlayerState

  def changeState(): Unit

  def addShot(x: Int, y: Int): Unit

  def isLost: Boolean

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

  def GameStateText: String

  def resetGame(): Unit

  def saveGame(): Unit

  def loadGame(): Unit


}
