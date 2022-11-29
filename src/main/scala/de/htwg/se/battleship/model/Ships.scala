package de.htwg.se.battleship.model

import scala.util.control.NonLocalReturns.*

trait Ships() {

  def size: Int

  def isHIt(x: Int, y: Int): Boolean


}
private class ShipSizeTwo(x: Vector[Int], y: Vector[Int]) extends Ships {

  override def size: Int = 2

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 2er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }


}

private class ShipSizeThree(x: Vector[Int], y: Vector[Int]) extends Ships {


  override def size: Int = 3

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 3er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }
}

private class ShipSizeFour(x: Vector[Int], y: Vector[Int]) extends Ships {

  override def size: Int = 4

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 4er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }
}

private class ShipSizeFive(x: Vector[Int], y: Vector[Int]) extends Ships {

  override def size: Int = 5

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 5er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }


}

object Ship {

  def apply(x: Vector[Int], y: Vector[Int], size: Int): Ships = {
    size match {
      case 2 => new ShipSizeTwo(x, y)
      case 3 => new ShipSizeThree(x, y)
      case 4 => new ShipSizeFour(x, y)
      case 5 => new ShipSizeFive(x, y)
    }
  }

}
