package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShotSpec extends AnyWordSpec {
  val shot: Shot = new Shot
  "Shots" should {
    "have a addShot method" in {
      shot.addShot(1,3) should be (0)
    }
    "have a getX method" in {
      shot.getX(1) should be (1)
    }
    "have a getY method" in {
      shot.getY(1) should be(3)
    }
  }

}
