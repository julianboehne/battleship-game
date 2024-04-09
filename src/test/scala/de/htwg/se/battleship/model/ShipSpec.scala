package de.htwg.se.battleship.model


import de.htwg.se.battleship.model.gridImpl.Ship
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShipSpec extends AnyWordSpec {

  val ship2: Ship = Ship(Vector[Int](1, 1), Vector[Int](2, 3), 2) // size 2 ship
  val ship3: Ship = Ship(Vector[Int](1, 1, 1), Vector[Int](2, 3, 4), 3) // size 3 ship
  val ship4: Ship = Ship(Vector[Int](1, 1, 1, 1), Vector[Int](1, 2, 3, 4), 4) // size 4 ship
  val ship5: Ship = Ship(Vector[Int](1, 1, 1, 1, 1), Vector[Int](1, 2, 3, 4, 5), 5) // size 5 ship

  "Shots" should {
    "have a getX and getY function" in {
      // ship2
      ship2.getX(0) should be(1)
      ship2.getY(0) should be(2)

      //ship3
      ship3.getX(0) should be(1)
      ship3.getY(0) should be(2)

      //ship4
      ship4.getX(0) should be(1)
      ship4.getY(0) should be(1)

      //ship5
      ship5.getX(0) should be(1)
      ship5.getY(0) should be(1)
    }
    "have a size" in {
      ship2.size should be(2)
      ship3.size should be(3)
      ship4.size should be(4)
      ship5.size should be(5)
    }
    "have a isHit function" in {
      ship2.isHit(1, 2) should be(true)
      ship3.isHit(1, 2) should be(true)
      ship4.isHit(1, 2) should be(true)
      ship5.isHit(1, 2) should be(true)

      ship2.isHit(8, 8) should be(false)
      ship3.isHit(8, 8) should be(false)
      ship4.isHit(8, 8) should be(false)
      ship5.isHit(8, 8) should be(false)
    }
    "have a getVectorX and getVectorY function" in {
      ship2.getVectorX should be(Vector(1, 1))
      ship2.getVectorY should be(Vector(2, 3))

      ship3.getVectorX should be(Vector(1, 1, 1))
      ship3.getVectorY should be(Vector(2, 3, 4))

      ship4.getVectorX should be(Vector(1, 1, 1, 1))
      ship4.getVectorY should be(Vector(1, 2, 3, 4))

      ship5.getVectorX should be(Vector(1, 1, 1, 1, 1))
      ship5.getVectorY should be(Vector(1, 2, 3, 4, 5))
    }

  }


}
