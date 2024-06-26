package model

import core.model.gridImpl.EmptyGrid
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class EmptyGridSpec extends AnyWordSpec {
  val test = EmptyGrid(10)
  "EmptyFrid" should {
    "have a field function" in {

      test.field(1, 1) should be("+-+" + test.nextline + "| |" + test.nextline + "+-+" + test.nextline)
    }
    "have a fullField function" in {
      val einser = EmptyGrid(1)
      einser.fullField should be("+----+" + einser.nextline + "|    |" + einser.nextline + "+----+" + einser.nextline)
      einser.toString should be("+----+" + einser.nextline + "|    |" + einser.nextline + "+----+" + einser.nextline)
    }
  }


}
