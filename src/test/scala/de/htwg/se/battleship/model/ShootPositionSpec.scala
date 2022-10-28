package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ShootPositionSpec extends AnyWordSpec {
  "ShootPosition" should {
    val test = ShootPosition('B', 3)
    "have a getPosition method" in {
      test.getPosition should be ("B3")
    }
  }

}
