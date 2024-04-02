package de.htwg.se.battleship.model.fileIOImpl

import com.google.inject.Inject
import de.htwg.se.battleship.controller.GameState
import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

import java.io.*
import play.api.libs.json.*
import scala.util.*
import scala.io.Source

class FileIOJson extends FileIOInterface {

  override def save(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState): Unit = {
    println("saving")

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(gameStateToJson(state1, state2, currentState, gameState)))
    pw.close()

  }

  private def gameStateToJson(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState) = {
    Json.obj(

      "general" -> Json.obj(
        "currentState" -> JsNumber(currentState),
        "gameState" -> JsString(gameState.toString)
      ),
      "state1" -> Json.obj(
        "name" -> JsString(state1.getPlayerName),
        "grid" -> Json.obj(
          "size" -> JsNumber(state1.grid.size),
          "shots" -> Json.obj(
            "X" -> state1.grid.shots.X.toArray,
            "Y" -> state1.grid.shots.Y.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> (0 until state1.grid.ships.getSize).map(i => state1.grid.ships.shipsVector(i).x).toArray,
              "Y" -> (0 until state1.grid.ships.getSize).map(i => state1.grid.ships.shipsVector(i).y).toArray

            )
          )
        )
      ),

      "state2" -> Json.obj(
        "name" -> JsString(state2.getPlayerName),
        "grid" -> Json.obj(
          "size" -> JsNumber(state2.grid.size),
          "shots" -> Json.obj(
            "X" -> state2.grid.shots.X.toArray,
            "Y" -> state2.grid.shots.Y.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> (0 until state2.grid.ships.getSize).map(i => state2.grid.ships.shipsVector(i).x).toArray,
              "Y" -> (0 until state2.grid.ships.getSize).map(i => state2.grid.ships.shipsVector(i).y).toArray

            )
          )
        )
      )
    )

  }

  //override def load(): Vector[PlayerState] = ???

  override def load(): (PlayerState, PlayerState, Int, GameState) = {
    val source: String = Source.fromFile("gameState.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val name1 = (json \ "state1" \ "name").get.toString.replaceAll("^\"|\"$", "")
    val name2 = (json \ "state2" \ "name").get.toString.replaceAll("^\"|\"$", "")

    val gridSize1 = (json \ "state1" \ "grid" \ "size").get.toString.toInt
    val gridSize2 = (json \ "state2" \ "grid" \ "size").get.toString.toInt


    val shotsX1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "X").get.toString match
      case s:String if s == "[]" => Vector.empty[Int]
      case s:String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsY1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "Y").get.toString match
      case s:String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsX2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "X").get.toString match
      case s:String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsY2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "Y").get.toString match
      case s:String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector


    val shots1 = Shots(shotsX1, shotsY1)
    val shots2 = Shots(shotsX2, shotsY2)

    val shipsX1: Vector[Vector[Int]] = Vector((json \ "state1" \ "grid" \ "ships" \ "shipsVector" \ "X").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsY1: Vector[Vector[Int]] = Vector((json \ "state1" \ "grid" \ "ships" \ "shipsVector" \ "Y").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsX2: Vector[Vector[Int]] = Vector((json \ "state2" \ "grid" \ "ships" \ "shipsVector" \ "X").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsY2: Vector[Vector[Int]] = Vector((json \ "state2" \ "grid" \ "ships" \ "shipsVector" \ "Y").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)


    val shipContainer1 = ShipContainer(shipsX1.zip(shipsY1).map { case (x, y) => Ship(x, y, x.size) })
    val shipContainer2 = ShipContainer(shipsX2.zip(shipsY2).map { case (x, y) => Ship(x, y, x.size) })


    val grid1 = Grid(gridSize1, shots1, shipContainer1)
    val grid2 = Grid(gridSize2, shots2, shipContainer2)

    val state1 = new Player1State(grid1, name1)
    val state2 = new Player1State(grid2, name2)

    val currentState = (json \ "general" \ "currentState").get.toString.toInt

    val gameStateStr:String = (json \ "general" \ "gameState").get.toString.replaceAll("^\"|\"$", "")
    val gameState: GameState = GameState.determineGameState(gameStateStr)

    (state1, state2, currentState, gameState)
  }

}
