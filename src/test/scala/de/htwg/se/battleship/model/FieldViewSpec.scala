package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.Source


class FieldViewSpec extends AnyWordSpec {
  val test: FieldView = FieldView(4, 10)
  val shots: Shot = Shot()
  "Game" should {
    "have a startSetup method" in {
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/startSetup.txt")
      val str = source.mkString
      source.close
      test.startSetup() should be(str)
    }
    "have a setShot method" in {
      shots.addShot(2, 3, false)
      test.setShot(shots) should be(test.loop(0, shots))
    }
    "have a loop method" in {
      val source1 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/loop1.txt")
      val str1 = source1.mkString
      source1.close
      test.loop(0, shots) should be(str1)

      val source2 = Source.fromFile("src/test/scala/de/htwg/se/battleship/model/review/loop2.txt")
      val str2 = source2.mkString
      source2.close
      shots.addShot(5, 8, true)
      test.loop(0, shots) should be(str2)
    }

  }

}
