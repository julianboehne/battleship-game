package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.Source


class FieldStrutureSpec extends AnyWordSpec {
  "Field" should {
    val f = FieldStruture()
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
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/FieldPrint.txt")
      val str = source.mkString
      source.close
      f.fieldPrint(4, 10) should be(str)
    }
    "have a vertical shot method" in {
      f.vertical(4, 1, 1) should be("|  X |" + f.nextline)
    }
    "have a updateFieldPrint method" in {
      val source1 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/updateFieldPrint1.txt")
      val str1 = source1.mkString
      source1.close
      f.updateFieldPrint(4,10,3,7) should be(str1)

      val source2 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/updateFieldPrint2.txt")
      val str2 = source2.mkString
      source2.close
      f.updateFieldPrint(4,10,8,2) should be(str2)
    }
    "have a updateField method" in {
      f.updateField(4, 2, 1, 1) should be("+----+----+" + f.nextline + "|  X |    |" + f.nextline + "+----+----+" + f.nextline + "|    |    |" + f.nextline + "+----+----+" + f.nextline)
    }
  }
}
