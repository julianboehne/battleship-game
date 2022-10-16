package de.htwg.se.battleship

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class TicTacToeSpec extends AnyWordSpec {
  "TicTacToe" should {
    "have a horizontal as String of form '+---+---+---+'" in {
      horizontal() should be("+---+---+---+" + nextLine)
    }
    "have a scalable bar" in {
      horizontal(1, 1) should be("+-+" + nextLine)
      horizontal(1, 2) should be("+-+-+" + nextLine)
      horizontal(2, 1) should be("+--+" + nextLine)
    }
    "have vertical as String of form '|   |   |   |'" in {
      vertical() should be("|   |   |   |" + nextLine)
    }
    "have scalable cell" in {
      vertical(1, 1) should be("| |" + nextLine)
      vertical(1, 2) should be("| | |" + nextLine)
      vertical(2, 1) should be("|  |" + nextLine)
    }
    "have a field in the form " +
      "+-+  " +
      "| |" +
      "+-+" in {
      field(1, 1) should be("+-+" + nextLine + "| |" + nextLine + "+-+" + nextLine)
    }
  }
}