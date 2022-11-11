package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldViewSpec extends AnyWordSpec {
  val test: FieldView = FieldView(4, 10)
  "Game" should {
    "have a startSetup method" in {
      test.startSetup() should be (0)
    }
    "have a setShot method" in {
      test.setShot(1, 2) should be (0)
    }

  }

}
