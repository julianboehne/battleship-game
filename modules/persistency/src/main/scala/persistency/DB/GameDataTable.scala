package persistency.DB

import slick.jdbc.PostgresProfile.api.*
import slick.lifted.{ForeignKey, ForeignKeyQuery}
import scala.annotation.targetName


class GameDataTable(tag: Tag) extends Table[(Option[Int], Int, String, Int, Int, String, String)](tag, "GAME_DATA") {

  def id = column[Option[Int]]("ID", O.PrimaryKey, O.AutoInc)
  def currentState = column[Int]("CURRENT_STATE_ID")
  def gameState = column[String]("GAME_STATE_ID")
  def gridSize1 = column[Int]("GRID_SIZE1_ID")
  def gridSize2 = column[Int]("GRID_SIZE2_ID")
  def name1 = column[String]("NAME1_ID")
  def name2 = column[String]("NAME2_ID")

  def * = (id, currentState, gameState, gridSize1, gridSize2, name1, name2)
}

