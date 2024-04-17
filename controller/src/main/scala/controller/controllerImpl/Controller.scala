package controller.controllerImpl

import com.google.inject.{Guice, Inject}
import controller.{BattleshipModule, ControllerInterface}
import util.GameState.*
import util.state.{Player1State, Player2State, PlayerState}
import model.*
import model.gridImpl.Grid
import persistency.*

import scala.util.control.NonLocalReturns.*
import util.{GameState, Observable, UndoManager}

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
    val injector = Guice.createInjector(new BattleshipModule)
    val fileIo = injector.getInstance(classOf[FileIOInterface])
    val player = if (state.isInstanceOf[Player1State]) 1 else 2
    fileIo.save(player1, player2, player, gameState)
  }

  override def loadGame(): Unit = {
    val injector = Guice.createInjector(new BattleshipModule)
    val fileIo = injector.getInstance(classOf[FileIOInterface])

    val vec = fileIo.load()

    player1 = new Player1State(vec(0).grid, vec(0).getPlayerName)
    player2 = new Player2State(vec(1).grid, vec(1).getPlayerName)

    state = if (vec(2) == 1) player1 else player2

    gameState = vec(3)
  }

  override def toString: String = state.grid.getGridShots
}