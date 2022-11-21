package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import scala.io.Source

class ControllerSpec extends AnyWordSpec {

  val field: FieldView = FieldView(4, 10)
  val shots: Shot = new Shot
  val controller: Controller = Controller(field)

  "Controller" should {
    "have a method gameSetup:" in {
      controller.gameSetup() should be(field.startSetup())
    }
    "have a method addShot" in {
      controller.addShot(5, 7) should be(0)
    }
    "have a toString method" in {
      val source = Source.fromFile("src/test/scala/de/htwg/se/battleship/controller/review/toString.txt")
      val str = source.mkString
      source.close
      controller.toString should be(str)
    }
  }

}
 