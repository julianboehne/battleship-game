package model

import org.scalatest._
import gridImpl.{Ship, ShipContainer}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShipContainerSpec extends AnyWordSpec {

  "A ShipContainer" should {
    "add a ship to the shipsVector" in {

      val ship = Ship(Vector(1, 2), Vector(1, 2), 2)
      val shipContainer = ShipContainer(Vector.empty)

      shipContainer.addShip(ship).shipsVector should contain(ship)
    }


    "remove a ship from the shipsVector" in {
      val ship = Ship(Vector(1, 2), Vector(1, 2), 2)
      val shipContainer = ShipContainer(Vector(ship))

      shipContainer.removeShip().shipsVector shouldBe empty
    }

    "return the correct size of the shipsVector" in {
      val ship1 = Ship(Vector(1, 2), Vector(1, 2), 2)
      val ship2 = Ship(Vector(3, 4), Vector(3, 4), 2)

      ShipContainer(Vector(ship1, ship2)).getSize should be(2)
    }

    "return true if the ship position is valid" in {
      val shipContainer = ShipContainer(Vector.empty)

      shipContainer.isValid(1, 2, 1, 4) should be(true)
    }
    "return false if the ship position is invalid" in {
      val shipContainer = ShipContainer(Vector.empty)

      shipContainer.isValid(1, 2, 3, 4) should be(false)
    }

    "return a ship with the correct position and size" in {
      val shipContainer = ShipContainer(Vector.empty)
      val ship = shipContainer.getShip(1, 2, 1, 4)
      val ship2 = shipContainer.getShip(1, 2, 3, 2)

      val ship3 = shipContainer.getShip(1, 4, 1, 2)
      val ship4 = shipContainer.getShip(3, 2, 1, 2)
      ship.size should be(3)
      ship2.size should be(3)

      ship3.size should be(3)
      ship4.size should be(3)
      ship.getVectorX should be(Vector(1, 1, 1))
      ship.getVectorY should be(Vector(2, 3, 4))

      ship2.getVectorX should be(Vector(1, 2, 3))
      ship2.getVectorY should be(Vector(2, 2, 2))

      ship3.getVectorX should be(Vector(1, 1, 1))
      ship3.getVectorY should be(Vector(4, 3, 2))

      ship4.getVectorX should be(Vector(3, 2, 1))
      ship4.getVectorY should be(Vector(2, 2, 2))
    }

    "return true if the total ship count is valid and false if not" in {
      val ship1 = Ship(Vector(1, 2), Vector(1, 1), 2)
      val ship2 = Ship(Vector(1, 2, 3), Vector(2, 2, 2), 3)
      val ship3 = Ship(Vector(1, 2, 3, 4), Vector(3, 3, 3, 3), 4)
      val ship4 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)


      val shipContainer = ShipContainer(Vector(ship1, ship2, ship3, ship4))

      shipContainer.shipCountValid() should be(true)

      val ship5 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)
      val ship6 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)
      val ship7 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)
      val ship8 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)

      val shipContainer2 = ShipContainer(Vector(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8))

      shipContainer2.shipCountValid() should be(false)
    }

    "determine if the single count of ships is valid" in {
      val ship1_1 = Ship(Vector(1, 2), Vector(1, 1), 2)
      val ship1_2 = Ship(Vector(1, 2), Vector(1, 1), 2)
      val ship1_3 = Ship(Vector(1, 2), Vector(1, 1), 2)
      val ship2_1 = Ship(Vector(1, 2, 3), Vector(2, 2, 2), 3)
      val ship2_2 = Ship(Vector(1, 2, 3), Vector(2, 2, 2), 3)
      val ship3_1 = Ship(Vector(1, 2, 3, 4), Vector(3, 3, 3, 3), 4)
      val ship3_2 = Ship(Vector(1, 2, 3, 4), Vector(3, 3, 3, 3), 4)
      val ship4_1 = Ship(Vector(1, 2, 3, 4, 1), Vector(4, 4, 4, 4, 4), 5)

      val shipContainer = ShipContainer(Vector(ship1_1))
      shipContainer.shipSingleCountValid() should be(true)

      val shipContainer2 = ShipContainer(Vector(ship1_1, ship1_2, ship1_3, ship2_1, ship2_2, ship3_1, ship3_2, ship4_1))
      shipContainer2.shipSingleCountValid() should be(true)

      val shipContainer3 = ShipContainer(Vector(ship1_1, ship1_2, ship1_3, ship1_1, ship2_1, ship2_2, ship3_1, ship3_2, ship4_1))
      shipContainer3.shipSingleCountValid() should be(false)
    }

    "determine if a ship has been hit" in {
      val ship = Ship(Vector(1, 2), Vector(1, 1), 2)
      val shipContainer = ShipContainer(Vector(ship))

      shipContainer.isHit(1, 1) should be(true)
    }

    "return true if all ship positions are unique" in {
      val ship1 = Ship(Vector(1, 2), Vector(1, 2), 2)
      val ship2 = Ship(Vector(3, 4), Vector(3, 4), 2)

      ShipContainer(Vector(ship1, ship2)).shipPosition() should be(true)
    }

    "return false if two ships have the same position" in {
      val ship1 = Ship(Vector(1, 2), Vector(1, 2), 2)
      val ship2 = Ship(Vector(1, 2), Vector(1, 2), 2)

      ShipContainer(Vector(ship1, ship2)).shipPosition() should be(false)
    }

  }

}
