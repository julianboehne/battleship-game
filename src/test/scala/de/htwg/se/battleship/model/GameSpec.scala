package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameSpec extends AnyWordSpec {
  val test: Game = Game()
  "Game" should {
    "have a startSetup method" in {
      test.startSetup() should be (0)
    }
    "have a setShot method" in {
      test.setShot(1, 2) should be (0)
    }

  }

}
