package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class FieldSpec extends AnyWordSpec {
  "Field" should {
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
    "have a vertical shot method" in {
      f.vertical(4,1, 1) should be ("|  X |" + f.nextline)
    }
    "have a updateFieldPrint method" in {
      f.updateFieldPrint(4,10,5,7) should be (0)
    }
    "have a updateField method" in {
      f.updateField(4,2,1,1) should be ("+----+----+" + f.nextline +"|  X |    |" + f.nextline + "+----+----+" + f.nextline + "|    |    |" + f.nextline + "+----+----+" + f.nextline)
    }
  }
}
