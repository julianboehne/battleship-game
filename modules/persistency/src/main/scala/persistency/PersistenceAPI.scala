package persistency

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.{FromStringUnmarshaller, Unmarshaller}
import com.google.inject.{Guice, Injector}
import persistency.DB.*
import persistency.DB.mongo.Mongo
import persistency.IO.{FileIOJson, FileIOXml}
import play.api.libs.json.{Json, Writes}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object PersistenceAPI {

  val json = new FileIOJson
  val xml = new FileIOXml
  val slick = new Slick
  val mongo = new Mongo

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "BattleshipHTTPServer")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    implicit val vectorIntUnmarshaller: FromStringUnmarshaller[Vector[Int]] =
      Unmarshaller.strict[String, Vector[Int]] { str =>
        Try {
          str.trim.stripPrefix("[").stripSuffix("]").split(",").toVector.map(_.trim.toInt)
        }.getOrElse(Vector.empty[Int])
      }


    implicit val vectorVectorIntUnmarshaller: FromStringUnmarshaller[Vector[Vector[Int]]] =
      Unmarshaller.strict[String, Vector[Vector[Int]]] { str =>
        Try {
          val trimmedStr = str.trim.stripPrefix("[").stripSuffix("]")

          trimmedStr.split("\\s*\\],\\s*\\[").toVector.map { innerStr =>
            innerStr.trim.stripPrefix("[").stripSuffix("]").split("\\s*,\\s*").toVector.map(_.trim.toInt)
          }
        }.getOrElse(Vector.empty[Vector[Int]])
      }


    val route = concat(
      path("persistence" / "load") {
        post {
          formFields(
            "format".as[String],
          ) { format =>
            val result = Try {
              val gameData = format match {
                case "json" => json.load()
                case "xml" => xml.load()
                case "slick" => slick.load()
                case "mongo" => mongo.load()
                case _ => throw new Exception("Invalid format --> 'json', 'xml' or 'slick'")
              }
              val jsonResult = createJsonResult(gameData)
              val jsonString = Json.stringify(jsonResult)
              complete(
                HttpEntity(ContentTypes.`application/json`, jsonString)
              )
            }
            handleResult(result)
          }
        }
      },
      path("persistence" / "save") {
        post {
          formFields(
            "format".as[String],
            "currentState".as[Int],
            "gameState".as[String],
            "gridSize1".as[Int],
            "gridSize2".as[Int],
            "name1".as[String],
            "name2".as[String],
            "shotsX1".as[Vector[Int]],
            "shotsY1".as[Vector[Int]],
            "shotsX2".as[Vector[Int]],
            "shotsY2".as[Vector[Int]],
            "shipsX1".as[Vector[Vector[Int]]],
            "shipsY1".as[Vector[Vector[Int]]],
            "shipsX2".as[Vector[Vector[Int]]],
            "shipsY2".as[Vector[Vector[Int]]]
          ) { (format, currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2) =>
            val result = Try {
              val gameData = GameData(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)
              format match {
                case "json" =>
                  json.save(gameData)
                case "xml" =>
                  xml.save(gameData)
                case "slick" =>
                  slick.save(gameData)
                case "mongo" =>
                  mongo.save(gameData)
              }
              complete(StatusCodes.OK, s"Save game succeeded")
            }
            handleResult(result)
          }
        }
      }
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8081).bind(route)
    println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine() // Lässt den Server laufen, bis der Benutzer Return drückt
    bindingFuture
      .flatMap(_.unbind()) // Löst die Bindung vom Port
      .onComplete(_ => system.terminate())

  }

  private def createJsonResult(gameData: GameData): play.api.libs.json.JsObject = {
    Json.obj(
      "currentState" -> gameData.currentState,
      "gameState" -> gameData.gameState,
      "gridSize1" -> gameData.gridSize1,
      "gridSize2" -> gameData.gridSize2,
      "name1" -> gameData.name1,
      "name2" -> gameData.name2,
      "shotsX1" -> gameData.shotsX1,
      "shotsY1" -> gameData.shotsY1,
      "shotsX2" -> gameData.shotsX2,
      "shotsY2" -> gameData.shotsY2,
      "shipsX1" -> gameData.shipsX1,
      "shipsY1" -> gameData.shipsY1,
      "shipsX2" -> gameData.shipsX2,
      "shipsY2" -> gameData.shipsY2
    )
  }

  private def handleResult(result: Try[Route]): Route = {
    result match {
      case Success(response) => response
      case Failure(e) =>
        println(s"Exception during bet creation: ${e.getMessage}")
        complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
    }
  }

}
