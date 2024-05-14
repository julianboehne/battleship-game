package core.controller.controllerImpl

import com.google.inject.{Guice, Inject}
import core.controller.ControllerInterface
import core.model.GridInterface
import core.model.gridImpl.*
import core.util.{GameState, Observable, UndoManager}
import core.util.state.{Player1State, Player2State, PlayerState}
import core.util.GameState.*
import core.model.*
import persistency.*
import persistency.DB.Slick
import persistency.IO.FileIOJson

import scala.util.control.NonLocalReturns.*

class Controller @Inject()(override val grid: GridInterface) extends ControllerInterface with Observable {

  val undoManager = new UndoManager
  var gameState: GameState = PLAYER_CREATE1

  var player1: PlayerState = new Player1State(grid, "")
  var player2: PlayerState = new Player2State(grid, "")
  var state: PlayerState = player1

  override def changeState(): Unit = {
    state match
      case _: Player1State => state = player2
      case _: Player2State => state = player1
  }

  override def addShot(x: Int, y: Int): Unit = {
    state.grid = Grid(grid.size, state.grid.shots.addShot(x, y), state.grid.ships)
    notifyObservers
  }

  override def isLost: Boolean = state.grid.getNumberSunk == state.grid.ships.getSize

  override def checkShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = state.grid.ships.isValid(x1, y1, x2, y2)

  override def alreadyFired(x: Int, y: Int): Boolean = state.grid.shots.X.contains(x) && state.grid.shots.Y.contains(y)

  override def GridShipToString: String = state.grid.getGridShips

  override def set(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    undoManager.doStep(new SetCommand(x1, y1, x2, y2, this))
    println(GridShipToString)
  }

  override def undo(): Unit = {
    undoManager.undoStep
    println(GridShipToString)
  }

  override def redo(): Unit = {
    undoManager.redoStep
    println(GridShipToString)
  }

  override def autoShips(): Unit = {
    this.set(1, 1, 1, 2)
    this.set(3, 1, 3, 2)
    this.set(10, 1, 9, 1)

    this.set(1, 7, 1, 9)
    this.set(2, 5, 2, 3)

    this.set(4, 6, 7, 6)
    this.set(10, 3, 10, 6)

    this.set(6, 1, 6, 5)
  }

  override def isValid(input: String): Boolean = input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

  override def getX(input: String): Int = {

    val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

    if (char.matches("[a-j]")) {
      return char.charAt(0) - 'a' + 1
    }
    char.charAt(0) - 'A' + 1

  }

  override def getY(input: String): Int = "(10)|([1-9])".r.findAllIn(input).mkString.toInt

  override def setPlayerName(name: String): Unit = {
    state match
      case _: Player1State => player1 = new Player1State(player1.grid, name)
      case _: Player2State => player2 = new Player2State(player2.grid, name)
  }

  override def GameStateText: String = GameState.message(gameState)

  override def resetGame(): Unit = {
    player1 = new Player1State(grid, "")
    player2 = new Player2State(grid, "")
    gameState = PLAYER_CREATE1
    state = player1
  }


  override def saveGame(): Unit = {
    val db = new Slick
    val player = if (state.isInstanceOf[Player1State]) 1 else 2
    val gameData = GameData(player, gameState.toString, player1.grid.size, player2.grid.size, player1.playerName.get, player2.playerName.get, player1.grid.shots.X, player1.grid.shots.Y, player2.grid.shots.X, player2.grid.shots.Y, (0 until player1.grid.ships.getSize).map(i => player1.grid.ships.shipsVector(i).x).toVector, (0 until player1.grid.ships.getSize).map(i => player1.grid.ships.shipsVector(i).y).toVector, (0 until player2.grid.ships.getSize).map(i => player2.grid.ships.shipsVector(i).x).toVector, (0 until player2.grid.ships.getSize).map(i => player2.grid.ships.shipsVector(i).y).toVector)
    db.save(gameData)
  }

  override def loadGame(): Unit = {
    val db = new Slick
    val gameData = db.load()

    val shots1 = Shots(gameData.shotsX1, gameData.shotsY1)
    val shots2 = Shots(gameData.shotsX2, gameData.shotsY2)

    val shipContainer1 = ShipContainer(gameData.shipsX1.zip(gameData.shipsY1).map { case (x, y) => Ship(x, y, x.size) })
    val shipContainer2 = ShipContainer(gameData.shipsX2.zip(gameData.shipsY2).map { case (x, y) => Ship(x, y, x.size) })

    val grid1 = Grid(gameData.gridSize1, shots1, shipContainer1)
    val grid2 = Grid(gameData.gridSize2, shots2, shipContainer2)

    val state1 = new Player1State(grid1, gameData.name1)
    val state2 = new Player1State(grid2, gameData.name2)

    player1 = new Player1State(state1.grid, state1.playerName.get)
    player2 = new Player2State(state2.grid, state2.playerName.get)

    state = if (gameData.currentState == 1) player1 else player2

    gameState = GameState.determineGameState(gameData.gameState)
  }

  override def toString: String = state.grid.getGridShots
}