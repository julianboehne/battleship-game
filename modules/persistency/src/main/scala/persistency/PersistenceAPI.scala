package persistency

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import persistency.IO.{FileIOJson, FileIOXml}
import play.api.libs.json.{Json, Writes}
import akka.http.scaladsl.unmarshalling.{FromStringUnmarshaller, Unmarshaller}
import scala.util.Try

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.Try

object PersistenceAPI {

  val json = new FileIOJson
  val xml = new FileIOJson

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
          // Remove the outer brackets and trim spaces
          val trimmedStr = str.trim.stripPrefix("[").stripSuffix("]")

          // Split the string into individual inner vectors, accounting for optional spaces around brackets
          trimmedStr.split("\\s*\\],\\s*\\[").toVector.map { innerStr =>
            // Remove any remaining brackets, trim spaces, and split by comma
            innerStr.trim.stripPrefix("[").stripSuffix("]").split("\\s*,\\s*").toVector.map(_.trim.toInt)
          }
        }.getOrElse(Vector.empty[Vector[Int]])
      }


    val route = concat(
      path("persistence" / "load") {
        post {
          formFields(
            "format".as[String],
          ) { (format) =>
            try {
              format match {
                case "json" =>
                  val gameData = json.load()
                  val jsonResult = Json.obj(
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
                  val jsonString = Json.stringify(jsonResult)
                  complete(
                    HttpEntity(ContentTypes.`application/json`, jsonString)
                  )
                case "xml" =>
                  val gameData = xml.load()
                  val jsonResult = Json.obj(
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
                  val jsonString = Json.stringify(jsonResult)
                  complete(
                    HttpEntity(ContentTypes.`application/json`, jsonString)
                  )
                case _ => complete(StatusCodes.BadRequest, "Invalid format --> 'json' or 'xml'")
              }
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
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
            try {
              format match {
                case "json" =>
                  json.save(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)
                case "xml" =>
                  xml.save(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)
              }
              complete(StatusCodes.OK, s"Save game succeeded")
            } catch {
              case e: Exception =>
                println(s"Exception during bet creation: ${e.getMessage}")
                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
            }
          }
        }
      },
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8081).bind(route)
    println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine() // Lässt den Server laufen, bis der Benutzer Return drückt
    bindingFuture
      .flatMap(_.unbind()) // Löst die Bindung vom Port
      .onComplete(_ => system.terminate())

  }

}
