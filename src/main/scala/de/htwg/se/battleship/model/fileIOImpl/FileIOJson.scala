package de.htwg.se.battleship.model.fileIOImpl

import com.google.inject.Inject
import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

import java.io.*
import play.api.libs.json.*

import scala.io.Source

class FileIOJson extends FileIOInterface {

  override def save(state1: PlayerState, state2: PlayerState): Unit = {
    println("saving")

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(gameStateToJson(state1, state2)))
    pw.close()

  }

  def gameStateToJson(state1: PlayerState, state2: PlayerState) = {
    Json.obj(
      "state1" -> Json.obj(
        "name" -> JsString(state1.getPlayerName),
        "grid" -> Json.obj(
          "size" -> JsNumber(state1.grid.getSize()),
          "shots" -> Json.obj(
            "X" -> (state1.grid.getShots().X.toArray),
            "Y" -> (state1.grid.getShots().Y.toArray)
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> (0 until state1.grid.getShips().getSize).map(i => state1.grid.getShips().shipsVector(i).x).toArray,
              "Y" -> (0 until state1.grid.getShips().getSize).map(i => state1.grid.getShips().shipsVector(i).y).toArray

            )
          )
        )
      ),

      "state2" -> Json.obj(
        "name" -> JsString(state2.getPlayerName),
        "grid" -> Json.obj(
          "size" -> JsNumber(state2.grid.getSize()),
          "shots" -> Json.obj(
            "X" -> (state2.grid.getShots().X.toArray),
            "Y" -> (state2.grid.getShots().Y.toArray)
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> (0 until state2.grid.getShips().getSize).map(i => state2.grid.getShips().shipsVector(i).x).toArray,
              "Y" -> (0 until state2.grid.getShips().getSize).map(i => state2.grid.getShips().shipsVector(i).y).toArray

            )
          )
        )
      )
    )

  }

  //override def load(): Vector[PlayerState] = ???

  override def load(): Vector[PlayerState] = {
    val source: String = Source.fromFile("gameState.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val name1 = (json \ "state1" \ "name").get.toString
    val name2 = (json \ "state2" \ "name").get.toString

    val gridSize1 = (json \ "state1" \ "grid" \ "size").get.toString.toInt
    val gridSize2 = (json \ "state2" \ "grid" \ "size").get.toString.toInt

    val shotsX1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "X").get.toString.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector
    val shotsY1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "Y").get.toString.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector
    val shotsX2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "X").get.toString.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector
    val shotsY2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "Y").get.toString.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shots1 = new Shots(shotsX1, shotsY1)
    val shots2 = new Shots(shotsX2, shotsY2)
    
    



    val grid1 = Grid(gridSize1, shots1, ShipContainer(Vector[Ship]()))
    val grid2 = Grid(gridSize2, shots2, ShipContainer(Vector[Ship]()))

    val state1 = new Player1State(grid1, name1)
    val state2 = new Player1State(grid2, name2)

    val stateVector: Vector[PlayerState] = Vector[PlayerState](state1, state2)
    stateVector
  }

}
