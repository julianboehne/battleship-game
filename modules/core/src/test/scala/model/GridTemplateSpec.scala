package model

import core.model.gridImpl.{Grid, GridTemplate, ShipContainer, Shots}
import org.scalatest.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class GridTemplateSpec extends AnyWordSpec {

  val nextline: String = sys.props("line.separator")

  "A GridTemplate" should {

    "create a field " in {
      val grid = new GridTemplate {
        override def fullField: String = field(4, 4)
      }



      grid.field(4, 4,4,4) should be(
          "+----+----+----+----+" + nextline +
          "|    |    |    |    |" + nextline +
          "+----+----+----+----+" + nextline +
          "|    |    |    |    |" + nextline +
          "+----+----+----+----+" + nextline +
          "|    |    |    |    |" + nextline +
          "+----+----+----+----+" + nextline +
          "|    |    |    |    |" + nextline +
          "+----+----+----+----+" + nextline
      )
    }


  }
}
