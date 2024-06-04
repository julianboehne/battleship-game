package persistency.DB.mongo

import com.mongodb.client.model.Updates.combine
import org.mongodb.scala.*
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.gridfs.{ObservableFuture, SingleObservableFuture}
import org.mongodb.scala.model.*
import org.mongodb.scala.model.Aggregates.*
import org.mongodb.scala.model.Filters.*
import org.mongodb.scala.model.Sorts.*
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import persistency.{DAOInterface, GameData}
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

class Mongo extends DAOInterface:
  private val database_pw = sys.env.getOrElse("MONGO_ROOT_PASSWORD", "123")
  private val database_username = sys.env.getOrElse("MONGO_ROOT_USERNAME", "root")
  private val host = sys.env.getOrElse("MONGO_HOST", "localhost")
  private val port = sys.env.getOrElse("MONGO_PORT", "27017")
  private val uri: String = s"mongodb://$database_username:$database_pw@$host:$port/?authSource=admin"
  private val client: MongoClient = MongoClient(uri)
  private val db: MongoDatabase = client.getDatabase("battleship")
  private val gameDataCollection: MongoCollection[Document] = db.getCollection("gameData")
  private val shipsCollection: MongoCollection[Document] = db.getCollection("ships")
  private val shotsCollection: MongoCollection[Document] = db.getCollection("shots")

  private def getLastId(collection: MongoCollection[Document]): Int =
    Await.result(collection.aggregate(Seq(
      Aggregates.sort(Sorts.descending("_id")),
      Aggregates.limit(1),
      Aggregates.project(Document("_id" -> 1))
    )).headOption(), Inf).flatMap(_.get("_id").map(_.asInt32().getValue.toString)).getOrElse("0").toInt

  private def getLastDocument(collection: MongoCollection[Document]): Option[Document] = {
    val filter = equal("_id", getLastId(collection))
    val futureDocument = collection.find(filter).first().toFutureOption()
    Await.result(futureDocument, 10.seconds)
  }

  private def insertDocument(collection: MongoCollection[Document], document: Document): Unit =
    Await.result(collection.insertOne(document).asInstanceOf[SingleObservable[Unit]].head(), 10.seconds)

  private def deleteAllDocuments(collection: MongoCollection[Document]): Unit =
    val filter = Document()
    Await.result(collection.deleteMany(filter).asInstanceOf[SingleObservable[Unit]].head(), 10.seconds)


  def save(gameData: GameData): Unit =
    val gameDataDocument = Document(
      "_id" -> (getLastId(gameDataCollection) + 1),
      "currentState" -> gameData.currentState,
      "gameState" -> gameData.gameState,
      "gridSize1" -> gameData.gridSize1,
      "gridSize2" -> gameData.gridSize2,
      "name1" -> gameData.name1,
      "name2" -> gameData.name2
    )
    insertDocument(gameDataCollection, gameDataDocument)

    val shipsDocument = Document(
      "_id" -> (getLastId(shipsCollection) + 1),
      "shipsX1" -> gameData.shipsX1.map(_.mkString(",")).mkString(";"),
      "shipsY1" -> gameData.shipsY1.map(_.mkString(",")).mkString(";"),
      "shipsX2" -> gameData.shipsX2.map(_.mkString(",")).mkString(";"),
      "shipsY2" -> gameData.shipsY2.map(_.mkString(",")).mkString(";")
    )
    insertDocument(shipsCollection, shipsDocument)

    val shotsDocument = Document(
      "_id" -> (getLastId(shotsCollection) + 1),
      "shotsX1" -> gameData.shotsX1.mkString(","),
      "shotsY1" -> gameData.shotsY1.mkString(","),
      "shotsX2" -> gameData.shotsX2.mkString(","),
      "shotsY2" -> gameData.shotsY2.mkString(",")
    )
    insertDocument(shotsCollection, shotsDocument)


  def load(): GameData =
    val gameDocument = getLastDocument(gameDataCollection)
    val shipsDocument = getLastDocument(shipsCollection)
    val shotsDocument = getLastDocument(shotsCollection)

    GameData(
      gameDocument.get("currentState").asInt32().getValue,
      gameDocument.get("gameState").asString().getValue,
      gameDocument.get("gridSize1").asInt32().getValue,
      gameDocument.get("gridSize2").asInt32().getValue,
      gameDocument.get("name1").asString().getValue,
      gameDocument.get("name2").asString().getValue,
      shotsDocument.get("shotsX1").asString().getValue.split(",").toVector.map(_.toInt),
      shotsDocument.get("shotsY1").asString().getValue.split(",").toVector.map(_.toInt),
      shotsDocument.get("shotsX2").asString().getValue.split(",").toVector.map(_.toInt),
      shotsDocument.get("shotsY2").asString().getValue.split(",").toVector.map(_.toInt),
      shipsDocument.get("shipsX1").asString().getValue.split(";").toVector.map(_.split(",").toVector.map(_.toInt)),
      shipsDocument.get("shipsY1").asString().getValue.split(";").toVector.map(_.split(",").toVector.map(_.toInt)),
      shipsDocument.get("shipsX2").asString().getValue.split(";").toVector.map(_.split(",").toVector.map(_.toInt)),
      shipsDocument.get("shipsY2").asString().getValue.split(";").toVector.map(_.split(",").toVector.map(_.toInt))

    )

  def delete(): Unit =
    val filter = Document()
    deleteAllDocuments(gameDataCollection)
    deleteAllDocuments(shipsCollection)
    deleteAllDocuments(shotsCollection)
