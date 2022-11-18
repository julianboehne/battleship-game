package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShotSpec extends AnyWordSpec {
  val shot: Shot = new Shot
  "Shots" should {
    "have a addShot method" in {
      shot.addShot(1, 3, true) should be(0)
    }
    "have a getX method" in {
      shot.getX(0) should be(1)
    }
    "have a getY method" in {
      shot.getY(0) should be(3)
    }
    "have a getHit method" in {
      shot.getHit(0) should be(true)
    }
  }

}
