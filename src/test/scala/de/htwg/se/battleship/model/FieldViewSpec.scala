package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import scala.io.Source


class FieldViewSpec extends AnyWordSpec {
  val test: FieldView = FieldView(4, 10)
  val shots: Shot = Shot()
  "Game" should {
    "have a startSetup method" in {
      val source = Source.fromFile("D:\\YARD\\GitHub\\battleship-game\\src\\test\\scala\\de\\htwg\\se\\battleship\\model\\review\\FieldViewSpec.txt")
      val str = source.mkString
      //println(str)
      source.close

      test.startSetup() should be(str)
    }
    "have a setShot method" in {
      shots.addShot(2, 3, false)
      test.setShot(shots) should be (test.loop(0,shots))
    }
    "have a loop method" in {
      test.loop(0,shots) should be (test.setShot(shots))
    }

  }

}
