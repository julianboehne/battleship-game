package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldViewSpec extends AnyWordSpec {
  val test: FieldView = FieldView(4, 10)
  val shots: Shot = Shot()
  "Game" should {
    "have a startSetup method" in {
      val field = Field()
      val str0 = s"${Console.RED} Enemy ${field.nextline}"
      val str1 = str0 + field.fieldPrint(4, 10) + field.nextline

      val str2 = str1 + s"${Console.BLUE} Enemy ${field.nextline}"
      val str3 = str2 + field.fieldPrint(4, 10) + field.nextline + Console.RESET
      test.startSetup() should be (str3)
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
