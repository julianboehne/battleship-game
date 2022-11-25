package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import scala.io.Source

class ControllerSpec extends AnyWordSpec {

  val field1: Field = Field(4, 10)
  val field2: Field = Field(4, 10)

  val shots: Shot = new Shot
  val controller: Controller = Controller(field1, field2)


  "Controller" should {
    "have a state:" in {
      controller.getField should be(field1)
      controller.setField() should be(1)
      controller.getField should be(field2)
      controller.setField() should be(0)
      controller.getField should be(field1)


    }
    "have a method gameSetup:" in {
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/controller/review/gameSetup.txt")
      val str = source.mkString
      source.close
      controller.gameSetup() should be(str)
    }
    "have a method addShot" in {
      controller.addShot(5, 7, field1) should be(0)
      controller.addShot(5, 7, field1) should be(1)
    }

    "have a toString method" in {
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/controller/review/toString.txt")
      val str = source.mkString
      source.close
      controller.toString should be(str)
    }
  }

}
 