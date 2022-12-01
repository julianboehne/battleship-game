package de.htwg.se.battleship.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShipsSpec extends AnyWordSpec {
  val ships: ShipContainer = ShipContainer()


  "Ships" should {
    "Have a allowed method" in {
      ships.allowed(5) should be(true)
      ships.allowed(5) should be(false)
    }

    "Have a addShip method" in {
      val x: Array[Int] = Array(1, 2, 3)
      val y: Array[Int] = Array(5,5,5)
      ships.addShip(x, y, x.length) should be(0)
      ships.addShip(x, y, x.length) should be(0)
      ships.addShip(x, y, x.length) should be(1)
    }

    "Have a isDone method" in {
      ships.isDone should be(false)
    }

  }

}
