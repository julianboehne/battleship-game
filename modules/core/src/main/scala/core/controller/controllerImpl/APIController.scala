package core.controller.controllerImpl


import akka.Done
import akka.actor.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.{FromStringUnmarshaller, Unmarshal, Unmarshaller}
import scala.concurrent.duration.*
import com.google.inject.{Guice, Inject}
import core.controller.ControllerInterface
import core.model.*
import core.model.gridImpl.*
import core.util.GameState.*
import core.util.state.{Player1State, Player2State, PlayerState}
import core.util.{GameState, Observable, UndoManager}
import play.api.libs.json.*
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.Future
import scala.util.Random
import scala.util.control.NonLocalReturns.*
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.control.NonLocalReturns.*
import scala.util.{Failure, Success, Try}

class APIController @Inject()(override val grid: GridInterface) extends ControllerInterface with Observable {

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

  override def autoShips(): Future[Done] = {
    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher

    val shipSizes = List(5, 4, 4, 3, 3, 2, 2, 2)
    val random = new Random()

    def generateRandomShip(size: Int): (Int, Int, Int, Int) = {
      val isHorizontal = random.nextBoolean()
      val x1 = random.nextInt(grid.size - (if (isHorizontal) size else 0)) + 1
      val y1 = random.nextInt(grid.size - (if (isHorizontal) 0 else size)) + 1
      val x2 = if (isHorizontal) x1 + size - 1 else x1
      val y2 = if (isHorizontal) y1 else y1 + size - 1
      (x1, y1, x2, y2)
    }

    def isValidShip(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
      val isLine = (x1 == x2 && y1 != y2) || (x1 != x2 && y1 == y2)

      val newShipPoints = if (x1 == x2) {
        (y1 to y2).map(y => (x1, y)).toSet
      } else {
        (x1 to x2).map(x => (x, y1)).toSet
      }

      val noOverlap = state.grid.ships.shipsVector.forall { ship =>
        val existingShipPoints = if (ship.x.head == ship.x.last) {
          ship.y.map(y => (ship.x.head, y)).toSet
        } else {
          ship.x.map(x => (x, ship.y.head)).toSet
        }
        existingShipPoints.intersect(newShipPoints).isEmpty
      }

      isLine && noOverlap
    }

    val source = Source(shipSizes)
    val flow = Flow[Int].map { size =>
      var ship: (Int, Int, Int, Int) = generateRandomShip(size)
      while (!isValidShip(ship._1, ship._2, ship._3, ship._4)) {
        ship = generateRandomShip(size)
      }
      ship
    }
    val sink = Sink.foreach[(Int, Int, Int, Int)] { case (x1, y1, x2, y2) =>
      this.set(x1, y1, x2, y2)
    }

    val graph = source.via(flow).toMat(sink)(Keep.right)
    graph.run()

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

  def formatVector[T](vector: Vector[T]): String = {
    "[" + vector.mkString(", ") + "]"
  }

  def formatShipsVector(ships: Vector[Vector[Int]]): String = {
    // Funktion zur Formatierung eines inneren Vektors
    def formatInnerVector(innerVector: Vector[Int]): String = {
      "[" + innerVector.mkString(", ") + "]"
    }

    // Anwendung der inneren Funktion auf jeden inneren Vektor und Zusammenführung der Ergebnisse
    val formattedShips = ships.map(innerVector => formatInnerVector(innerVector))
    "[" + formattedShips.mkString(", ") + "]"
  }


  override def saveGame(): Unit = {
    val player = if (state.isInstanceOf[Player1State]) 1 else 2

    println(player1.grid.shots.X.toString())
    println((0 until player1.grid.ships.getSize).map(i => player1.grid.ships.shipsVector(i).x).toVector.toString())

    val data = FormData(
      "format" -> "json",
      "currentState" -> player.toString,
      "gameState" -> gameState.toString,
      "gridSize1" -> player1.grid.size.toString,
      "gridSize2" -> player2.grid.size.toString,
      "name1" -> player1.playerName.get,
      "name2" -> player2.playerName.get,
      "shotsX1" -> formatVector(player1.grid.shots.X),
      "shotsY1" -> formatVector(player1.grid.shots.Y),
      "shotsX2" -> formatVector(player2.grid.shots.X),
      "shotsY2" -> formatVector(player1.grid.shots.Y),
      "shipsX1" -> formatShipsVector((0 until player1.grid.ships.getSize).map(i => player1.grid.ships.shipsVector(i).x).toVector),
      "shipsY1" -> formatShipsVector((0 until player1.grid.ships.getSize).map(i => player1.grid.ships.shipsVector(i).y).toVector),
      "shipsX2" -> formatShipsVector((0 until player2.grid.ships.getSize).map(i => player2.grid.ships.shipsVector(i).x).toVector),
      "shipsY2" -> formatShipsVector((0 until player2.grid.ships.getSize).map(i => player2.grid.ships.shipsVector(i).y).toVector)
    )

    implicit val system: ActorSystem = ActorSystem("httpRequestSystem")
    implicit val executionContext = system.dispatcher
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://persistence:8081/persistence/save",
      entity = data.toEntity
    )
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    responseFuture.onComplete {
      case Success(res) =>
        res.entity.toStrict(5.seconds).map { strictEntity =>
          val dataAsString = strictEntity.data.utf8String
          println(s"Received data: $dataAsString")
        }
      case Failure(ex) => println(s"Failed to send request: $ex")
    }

  }

  case class GameStateData(
                            currentState: Int,
                            gameState: String,
                            gridSize1: Int,
                            gridSize2: Int,
                            name1: String,
                            name2: String,
                            shotsX1: Vector[Int],
                            shotsY1: Vector[Int],
                            shotsX2: Vector[Int],
                            shotsY2: Vector[Int],
                            shipsX1: Vector[Vector[Int]],
                            shipsY1: Vector[Vector[Int]],
                            shipsX2: Vector[Vector[Int]],
                            shipsY2: Vector[Vector[Int]]
                          )

  override def loadGame(): Unit = {
    val data = FormData(
      "format" -> "json"
    )
    implicit val system: ActorSystem = ActorSystem("httpRequestSystem")
    implicit val executionContext = system.dispatcher
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://persistence:8081/persistence/load",
      entity = data.toEntity
    )
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    responseFuture.onComplete {
      case Success(res) =>
        res.entity.toStrict(5.seconds).map { strictEntity =>
          val dataAsString = strictEntity.data.utf8String
          println(s"Received data: $dataAsString")
          implicit val gameStateDataReads: Reads[GameStateData] = Json.reads[GameStateData]
          val gameStateData: JsResult[GameStateData] = Json.parse(dataAsString).validate[GameStateData]
          gameStateData match {
            case JsSuccess(gameData, _) => 
              println(s"Current State: ${gameData.currentState}")
              println(s"Game State: ${gameData.gameState}")
              println(s"Grid Size 1: ${gameData.gridSize1}")
              println(s"Grid Size 2: ${gameData.gridSize2}")
              println(s"Name 1: ${gameData.name1}")
              println(s"Name 2: ${gameData.name2}")
              println(s"Shots X 1: ${gameData.shotsX1}")
              println(s"Shots Y 1: ${gameData.shotsY1}")
              println(s"Shots X 2: ${gameData.shotsX2}")
              println(s"Shots Y 2: ${gameData.shotsY2}")
              println(s"Ships X 1: ${gameData.shipsX1}")
              println(s"Ships Y 1: ${gameData.shipsY1}")
              println(s"Ships X 2: ${gameData.shipsX2}")
              println(s"Ships Y 2: ${gameData.shipsY2}")
              
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
              
            case JsError(errors) => println("Fehler beim Parsen des JSON")
          }
        }
      case Failure(ex) => println(s"Failed to send request: $ex")
    }


  }

  override def toString: String = state.grid.getGridShots
}