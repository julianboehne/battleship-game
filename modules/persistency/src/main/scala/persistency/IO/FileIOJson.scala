package persistency.IO

import java.io.*
import scala.io.Source
import scala.util.*
import play.api.libs.json.*
import com.google.inject.Inject

import persistency.*

class FileIOJson extends DAOInterface {

  override def save(gameData: GameData): Unit = {
    println("saving")

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(gameStateToJson(gameData)))
    pw.close()

  }

  private def gameStateToJson(gameData: GameData) = {
    Json.obj(
      "general" -> Json.obj(
        "currentState" -> JsNumber(gameData.currentState),
        "gameState" -> JsString(gameData.gameState)
      ),
      "state1" -> Json.obj(
        "name" -> JsString(gameData.name1),
        "grid" -> Json.obj(
          "size" -> JsNumber(gameData.gridSize1),
          "shots" -> Json.obj(
            "X" -> gameData.shotsX1.toArray,
            "Y" -> gameData.shotsY1.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> gameData.shipsX1.toArray,
              "Y" -> gameData.shipsY1.toArray

            )
          )
        )
      ),

      "state2" -> Json.obj(
        "name" -> JsString(gameData.name2),
        "grid" -> Json.obj(
          "size" -> JsNumber(gameData.gridSize2),
          "shots" -> Json.obj(
            "X" -> gameData.shotsX2.toArray,
            "Y" -> gameData.shotsY2.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> gameData.shipsX2.toArray,
              "Y" -> gameData.shipsY2.toArray

            )
          )
        )
      )
    )

  }

  override def load(): (GameData) = {
    val source: String = Source.fromFile("gameState.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val name1 = (json \ "state1" \ "name").get.toString.replaceAll("^\"|\"$", "")
    val name2 = (json \ "state2" \ "name").get.toString.replaceAll("^\"|\"$", "")

    val gridSize1 = (json \ "state1" \ "grid" \ "size").get.toString.toInt
    val gridSize2 = (json \ "state2" \ "grid" \ "size").get.toString.toInt


    val shotsX1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "X").get.toString match
      case s: String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsY1: Vector[Int] = (json \ "state1" \ "grid" \ "shots" \ "Y").get.toString match
      case s: String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsX2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "X").get.toString match
      case s: String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shotsY2: Vector[Int] = (json \ "state2" \ "grid" \ "shots" \ "Y").get.toString match
      case s: String if s == "[]" => Vector.empty[Int]
      case s: String => s.stripPrefix("[").stripSuffix("]").split(",").map(_.toInt).toVector

    val shipsX1: Vector[Vector[Int]] = Vector((json \ "state1" \ "grid" \ "ships" \ "shipsVector" \ "X").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsY1: Vector[Vector[Int]] = Vector((json \ "state1" \ "grid" \ "ships" \ "shipsVector" \ "Y").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsX2: Vector[Vector[Int]] = Vector((json \ "state2" \ "grid" \ "ships" \ "shipsVector" \ "X").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)
    val shipsY2: Vector[Vector[Int]] = Vector((json \ "state2" \ "grid" \ "ships" \ "shipsVector" \ "Y").get.toString.split("[\\[\\]]").filterNot(_.isEmpty).map(_.split(",").map(_.toInt).toVector): _*).filter(_.nonEmpty)

    val currentState = (json \ "general" \ "currentState").get.toString.toInt

    val gameState: String = (json \ "general" \ "gameState").get.toString.replaceAll("^\"|\"$", "")

    GameData(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)
  }

}
