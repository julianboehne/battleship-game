package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldViewSpec extends AnyWordSpec {
  val test: FieldView = FieldView(4, 10)
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
      val x = 1
      val y = 2
      val field = Field()

      val str0 = s"x-Wert: $x \ny-Wert: $y" + field.nextline
      val str1 = str0 + "Shot Test" + field.nextline
      val str2 = str1 + field.updateFieldPrint(4, 10, x, y) + field.nextline
      test.setShot(x, y) should be (str2)
    }

  }

}
