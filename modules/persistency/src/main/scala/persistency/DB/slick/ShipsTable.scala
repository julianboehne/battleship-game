package persistency.DB

import slick.jdbc.PostgresProfile.api.*
import slick.lifted.{ForeignKey, ForeignKeyQuery}
import slick.jdbc.PostgresProfile.api._
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import scala.annotation.targetName

implicit val vectorVectorIntMapper: JdbcType[Vector[Vector[Int]]] with BaseTypedType[Vector[Vector[Int]]] =
  MappedColumnType.base[Vector[Vector[Int]], String](
    vv => vv.map(v => v.mkString(",")).mkString(";"),
    s => s.split(";").toVector.map(_.split(",").toVector.map(_.toInt))
  )

class ShipsTable(tag: Tag) extends Table[(Option[Int], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]])](tag, "SHIPS_DATA") {
  
  def id = column[Option[Int]]("ID", O.PrimaryKey, O.AutoInc)
  def shipsX1 = column[Vector[Vector[Int]]]("SHIPS_X1_ID")
  def shipsY1 = column[Vector[Vector[Int]]]("SHIPS_Y1_ID")
  def shipsX2 = column[Vector[Vector[Int]]]("SHIPS_X2_ID")
  def shipsY2 = column[Vector[Vector[Int]]]("SHIPS_Y2_ID")
  
  def * = (id, shipsX1, shipsY1, shipsX2, shipsY2)
}
