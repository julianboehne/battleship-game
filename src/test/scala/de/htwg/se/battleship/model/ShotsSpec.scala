package de.htwg.se.battleship.model


import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShotsSpec extends AnyWordSpec {

  val shot: Shots = Shots(Vector[Int](),Vector[Int]())
  "Shots" should {
    "have a addShot function" in {

      shot.addShot(1,1) should be(Shots(Vector[Int](1),Vector[Int](1)))
    }
    "have a gets function" in {
      val test = shot.addShot(1,2)
      test.getX(0) should be(1)
      test.getY(0) should be(2)

    }
    "should have two vectors for x and y-values" in {
      val test2 = shot.addShot(1,2)
      test2.X should be (Vector[Int](1))
      test2.Y should be (Vector[Int](2))
    }

  }


}
