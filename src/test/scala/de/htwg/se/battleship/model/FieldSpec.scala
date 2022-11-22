package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.Source


class FieldSpec extends AnyWordSpec {
  val test: Field = Field(4,10)
  "Game" should {
    "have empty field method" in {
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/emptyField.txt")
      val str = source.mkString
      source.close
      test.emptyField() should be(str)
    }
    "have a setShot method" in {
      test.shots.addShot(2,3,false)
      test.setShot() should be(test.field.nextline + test.loop(0))
    }
    "have a loop method" in {
      val source1 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/loop1.txt")
      val str1 = source1.mkString
      source1.close
      test.loop(0) should be(str1)

      val source2 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/loop2.txt")
      val str2 = source2.mkString
      source2.close
      test.shots.addShot(5, 8, true)
      test.loop(0) should be(str2)

      val source3 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/loop3.txt")
      val str3 = source3.mkString
      source3.close
      test.shots.addShot(10, 10, false)
      test.loop(0) should be(str3)
    }

  }

}
