package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ShootPositionSpec extends AnyWordSpec {
  val x = 'B'
  val y = 3
  "ShootPosition" should {
    val test = ShootPosition(x, y)
    "have a getPosition method" in {
      test.getPosition should be (x.toString + y.toString)
    }
  }

}
