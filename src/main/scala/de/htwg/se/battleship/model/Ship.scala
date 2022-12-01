package de.htwg.se.battleship.model

import scala.util.control.NonLocalReturns.*

trait Ship() {

  val size: Int

  def isHIt(x: Int, y: Int): Boolean

  def getX(index: Int): Int

  def getY(index: Int): Int


}
private class ShipSizeTwo(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 2

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 2er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)


}

private class ShipSizeThree(x: Vector[Int], y: Vector[Int]) extends Ship() {


  val size: Int = 3

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 3er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)

}

private class ShipSizeFour(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 4

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 4er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)

}

private class ShipSizeFive(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 5

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) {
        println("Du hast das 5er Schiff getroffen")
        throwReturn(true)
      }
    }
    false
  }

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)

}

object Ship {

  def apply(x: Vector[Int], y: Vector[Int], size: Int): Ship = {
    size match {
      case 2 => new ShipSizeTwo(x, y)
      case 3 => new ShipSizeThree(x, y)
      case 4 => new ShipSizeFour(x, y)
      case 5 => new ShipSizeFive(x, y)
    }
  }



}