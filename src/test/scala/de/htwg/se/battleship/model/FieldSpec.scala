package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class FieldSpec extends AnyWordSpec {
  "Battleship" should {
    val f = Field()
    "have a scalable bar" in {
      f.horizontal(1, 1) should be("+-+" + f.nextline)
      f.horizontal(1, 2) should be("+-+-+" + f.nextline)
      f.horizontal(2, 1) should be("+--+" + f.nextline)
    }
    "have scalable cell" in {
      f.vertical(1, 1) should be("| |" + f.nextline)
      f.vertical(1, 2) should be("| | |" + f.nextline)
      f.vertical(2, 1) should be("|  |" + f.nextline)
    }
    "have a field in the form " +
      "+-+  " +
      "| |" +
      "+-+" in {
      f.field(1, 1) should be("+-+" + f.nextline + "| |" + f.nextline + "+-+" + f.nextline)
    }
    "have a print method" in {
      f.fieldPrint(0, 0) should be (0)
    }
  }
}
