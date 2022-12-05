package de.htwg.se.battleship.model


import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShipSpec extends AnyWordSpec {

  val ship2: Ship = Ship(Vector[Int](1,1), Vector[Int](2,3), 2) // size 2 ship
  val ship3: Ship = Ship(Vector[Int](1,1,1), Vector[Int](2,3,4), 3) // size 2 ship
  val ship4: Ship = Ship(Vector[Int](1,1,1,1), Vector[Int](1,2,3,4), 4) // size 2 ship
  val ship5: Ship = Ship(Vector[Int](1,1,1,1,1), Vector[Int](1,2,3,4,5), 5) // size 2 ship

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
      ship2.isHIt(1,2) should be(true)
      ship3.isHIt(1,2) should be(true)
      ship4.isHIt(1,2) should be(true)
      ship5.isHIt(1,2) should be(true)

      ship2.isHIt(8,8) should be(false)
      ship3.isHIt(8,8) should be(false)
      ship4.isHIt(8,8) should be(false)
      ship5.isHIt(8,8) should be(false)
    }

  }


}
