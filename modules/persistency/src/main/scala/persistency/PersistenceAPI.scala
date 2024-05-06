package persistency

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.*
import akka.http.scaladsl.server.Directives.*
import com.google.inject.{Guice, Injector}
import persistency.IO.{FileIOJson, FileIOXml}
import play.api.libs.json.{Json, Writes}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.Try

object PersistenceAPI {

  val json = new FileIOJson
  val xml = new FileIOJson

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "BattleshipHTTPServer")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

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
//      path("persistence" / "save") {
//        post {
//          formFields(
//            "currentState".as[Int],
//            "gameState".as[String],
//            "gridSize1".as[Int],
//            "gridSize2".as[Int],
//            "name1".as[String],
//            "name2".as[String],
//            "shotsX1".as[Vector[Int]],
//            "shotsY1".as[Vector[Int]],
//            "shotsX2".as[Vector[Int]],
//            "shotsY2".as[Vector[Int]],
//            "shipsX1".as[Vector[Vector[Int]]],
//            "shipsY1".as[Vector[Vector[Int]]],
//            "shipsX2".as[Vector[Vector[Int]]],
//            "shipsY2".as[Vector[Vector[Int]]]
//          ) { () =>
//            try {
//              controller.addShot(x, y)
//              complete(StatusCodes.OK, s"Added shot successfully")
//            } catch {
//              case e: Exception =>
//                println(s"Exception during bet creation: ${e.getMessage}")
//                complete(StatusCodes.InternalServerError, s"Failed due to internal server error: ${e.getMessage}")
//            }
//          }
//        }
//      },
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // Lässt den Server laufen, bis der Benutzer Return drückt
    bindingFuture
      .flatMap(_.unbind()) // Löst die Bindung vom Port
      .onComplete(_ => system.terminate())

  }

}
