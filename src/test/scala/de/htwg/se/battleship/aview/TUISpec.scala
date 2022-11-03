package de.htwg.se.battleship.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._


class TUISpec extends AnyWordSpec {
  val test: TUI = TUI()
  "TUI" should {
    "have a isValid method" in {
      test.isValid("a5") should be (true)
      test.isValid("H10") should be (true)
      test.isValid("") should be (false)
      test.isValid("k5") should be (false)
      test.isValid("d11") should be (false)
    }
    "have a getX method" in {
      test.getX("a10") should be (1)
      test.getX("J5") should be (10)
      test.getX("c9") should be (3)
    }
    "have a getY method" in {
      test.getY("A10") should be(10)
      test.getY("h5") should be(5)
      test.getY("c9") should be(9)
    }
  }

}
