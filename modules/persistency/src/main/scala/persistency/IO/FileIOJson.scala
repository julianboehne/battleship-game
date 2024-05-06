package persistency.IO

import java.io.*
import scala.io.Source
import scala.util.*
import play.api.libs.json.*
import com.google.inject.Inject

import persistency.FileIOInterface

class FileIOJson extends FileIOInterface {

  override def save(currentState: Int, gameState: String, gridSize1: Int, gridSize2: Int, name1: String, name2: String, shotsX1: Vector[Int], shotsY1: Vector[Int], shotsX2: Vector[Int], shotsY2: Vector[Int], shipsX1: Vector[Vector[Int]], shipsY1: Vector[Vector[Int]], shipsX2: Vector[Vector[Int]], shipsY2: Vector[Vector[Int]]): Unit = {
    println("saving")

    val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(gameStateToJson(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)))
    pw.close()

  }

  private def gameStateToJson(currentState: Int, gameState: String, gridSize1: Int, gridSize2: Int, name1: String, name2: String, shotsX1: Vector[Int], shotsY1: Vector[Int], shotsX2: Vector[Int], shotsY2: Vector[Int], shipsX1: Vector[Vector[Int]], shipsY1: Vector[Vector[Int]], shipsX2: Vector[Vector[Int]], shipsY2: Vector[Vector[Int]]) = {
    Json.obj(
      "general" -> Json.obj(
        "currentState" -> JsNumber(currentState),
        "gameState" -> JsString(gameState)
      ),
      "state1" -> Json.obj(
        "name" -> JsString(name1),
        "grid" -> Json.obj(
          "size" -> JsNumber(gridSize1),
          "shots" -> Json.obj(
            "X" -> shotsX1.toArray,
            "Y" -> shotsY1.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> shipsX1.toArray,
              "Y" -> shipsY1.toArray

            )
          )
        )
      ),

      "state2" -> Json.obj(
        "name" -> JsString(name2),
        "grid" -> Json.obj(
          "size" -> JsNumber(gridSize2),
          "shots" -> Json.obj(
            "X" -> shotsX2.toArray,
            "Y" -> shotsY2.toArray
          ),
          "ships" -> Json.obj(
            "shipsVector" -> Json.obj(

              "X" -> shipsX2.toArray,
              "Y" -> shipsY2.toArray

            )
          )
        )
      )
    )

  }

  override def load(): (Int, String, Int, Int, String, String, Vector[Int], Vector[Int], Vector[Int], Vector[Int], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]]) = {
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

    (currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)
  }

}
