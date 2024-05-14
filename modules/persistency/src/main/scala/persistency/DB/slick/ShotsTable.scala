package persistency.DB

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcType
import slick.ast.BaseTypedType
import scala.annotation.targetName

implicit val vectorIntToStringMapper: JdbcType[Vector[Int]] with BaseTypedType[Vector[Int]] =
  MappedColumnType.base[Vector[Int], String](
    vector => vector.mkString(","),
    str => str.split(",").toVector.map(_.toInt))

class ShotsTable(tag: Tag) extends Table[(Option[Int], Vector[Int], Vector[Int], Vector[Int], Vector[Int])](tag, "SHOTS_DATA") {

  def id = column[Option[Int]]("ID", O.PrimaryKey, O.AutoInc)
  def shotsX1 = column[Vector[Int]]("SHOTS_X1_ID")
  def shotsY1 = column[Vector[Int]]("SHOTS_Y1_ID")
  def shotsX2 = column[Vector[Int]]("SHOTS_X2_ID")
  def shotsY2 = column[Vector[Int]]("SHOTS_Y2_ID")

  def * = (id, shotsX1, shotsY1, shotsX2, shotsY2)
}

