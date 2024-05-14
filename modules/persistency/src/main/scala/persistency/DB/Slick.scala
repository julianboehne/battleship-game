package persistency.DB

import akka.japi.JAPI
import persistency.GameData
import play.api.libs.json.Json
import slick.dbio.Effect.Read
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.sql.SQLNonTransientException
import scala.concurrent.Await
import scala.util.{Failure, Success}
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class Slick extends DAOInterface:
  private val databaseDB: String = sys.env.getOrElse("MYSQL_DATABASE", "tbl")
  private val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "postgres")
  private val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "postgres")
  private val databasePort: String = sys.env.getOrElse("MYSQL_PORT", "5432")
  private val databaseHost: String = sys.env.getOrElse("MYSQL_HOST", "database")
  private val databaseUrl = s"jdbc:postgresql://$databaseHost:$databasePort/$databaseDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true"

  private val WAIT_TIME = 5.seconds

  val database = Database.forURL(
    url = databaseUrl,
    driver = "org.postgresql.Driver",
    user = databaseUser,
    password = databasePassword
  )

  private val gameDataTable = TableQuery(GameDataTable(_))
  private val shipsTable = TableQuery(ShipsTable(_))
  private val shotsTable = TableQuery(ShotsTable(_))

  private val setup: DBIOAction[Unit, NoStream, Effect.Schema] = DBIO.seq(
    gameDataTable.schema.createIfNotExists,
    shipsTable.schema.createIfNotExists,
    shotsTable.schema.createIfNotExists
  )

  database.run(setup).onComplete {
    case Success(value) => println("tables created")
    case Failure(exception) => println(exception.getMessage)
  }

  def save(gameData: GameData): Unit =
    val gameStateInsert = gameDataTable returning gameDataTable.map(_.id) += (
      None,
      gameData.currentState,
      gameData.gameState,
      gameData.gridSize1,
      gameData.gridSize2,
      gameData.name1,
      gameData.name2
    )
    val shipsInsert = shipsTable returning shipsTable.map(_.id) += (
      None,
      gameData.shipsX1,
      gameData.shipsY1,
      gameData.shipsX2,
      gameData.shipsY2
    )
    val shotsInsert = shotsTable returning shotsTable.map(_.id) += (
      None,
      gameData.shotsX1,
      gameData.shotsY1,
      gameData.shotsX2,
      gameData.shotsY2
    )
    val gameDataId = Await.result(database.run(gameStateInsert), WAIT_TIME)
    val shipsId = Await.result(database.run(shipsInsert), WAIT_TIME)
    val shotsId = Await.result(database.run(shotsInsert), WAIT_TIME)


  def load(): GameData =
    val gameDataQuery = gameDataTable
      .join(shipsTable).on(_.id === _.id)
      .join(shotsTable).on(_._1.id === _.id)
      .sortBy(_._1._1.id.desc)
      .take(1)
    val action = gameDataQuery.result
    val result = Await.result(database.run(action), WAIT_TIME).head
    GameData(
      currentState = result._1._1._2,
      gameState = result._1._1._3,
      gridSize1 = result._1._1._4,
      gridSize2 = result._1._1._5,
      name1 = result._1._1._6,
      name2 = result._1._1._7,
      shipsX1 = result._1._2._2,
      shipsY1 = result._1._2._3,
      shipsX2 = result._1._2._4,
      shipsY2 = result._1._2._5,
      shotsX1 = result._2._2,
      shotsY1 = result._2._3,
      shotsX2 = result._2._4,
      shotsY2 = result._2._5
    )

  def delete(): Unit =
    val gameDataDelete = gameDataTable.delete
    val shipsDelete = shipsTable.delete
    val shotsDelete = shotsTable.delete

    database.run(gameDataDelete)
    database.run(shipsDelete)
    database.run(shotsDelete)
