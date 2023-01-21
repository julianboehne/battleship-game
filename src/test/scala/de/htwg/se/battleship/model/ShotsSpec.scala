package de.htwg.se.battleship.model


import de.htwg.se.battleship.model.gridImpl.Shots
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import de.htwg.se.battleship.model.gridImpl.Shots

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShotsSpec extends AnyWordSpec {

  "A Shots" should {

    "add a shot to the X and Y vectors" in {
      val shots = Shots(Vector(), Vector())

      shots.addShot(1, 1).X should be(Vector(1))
      shots.addShot(1, 1).Y should be(Vector(1))
    }

    "check if a location was shot" in {
      val shots = Shots(Vector(1), Vector(1))

      shots.wasShot(1, 1) should be(true)
      shots.wasShot(1, 2) should be(false)
    }

    "get the x-coordinate of a shot" in {
      val shots = Shots(Vector(1), Vector(1))

      shots.getX(0) should be(1)
    }

    "get the y-coordinate of a shot" in {
      val shots = Shots(Vector(1), Vector(1))

      shots.getY(0) should be(1)
    }

    "get the latest x-coordinate of a shot" in {
      val shots = Shots(Vector(1), Vector(1))

      shots.getLatestX should be(1)
    }

    "get the latest y-coordinate of a shot" in {
      val shots = Shots(Vector(1), Vector(1))

      shots.getLatestY should be(1)
    }
  }
}

