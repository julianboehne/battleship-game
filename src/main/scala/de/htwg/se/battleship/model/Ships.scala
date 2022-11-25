package de.htwg.se.battleship.model

import scala.util.control.NonLocalReturns.{returning, throwReturn}

class ShipContainer() {

  var shipTwoCount: Int = 3
  var shipThreeCount: Int = 2
  var shipFourCount: Int = 2
  var shipFiveCount: Int = 1

  var count: Int = 0
  val limit: Int = shipTwoCount + shipThreeCount + shipFourCount + shipFiveCount

  val ships: Array[Ship] = new Array[Ship](limit)

  def allowed(size: Int): Boolean = {
    size match {
      case 2 => if (shipTwoCount == 0) return false else shipTwoCount -= 1
      case 3 => if (shipThreeCount == 0) return false else shipThreeCount -= 1
      case 4 => if (shipFourCount == 0) return false else shipFourCount -= 1
      case 5 => if (shipFiveCount == 0) return false else shipFiveCount -= 1
    }
    true
  }

  def addShip(x: Array[Int], y: Array[Int], size: Int): Int = {
    if (!allowed(size)) return 1
    ships(count) = Ship(x, y, size)
    count += 1
    0
  }

  def isDone: Boolean = {
    if (count == limit) return true
    false
  }

}


trait Ship() {
  def count: Int = 0

  def size: Int

  def isHIt(x: Int, y: Int): Boolean

}

private class ShipSizeTwo(x: Array[Int], y: Array[Int]) extends Ship {

  override def size: Int = 2

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) then throwReturn(true)
    }
    false
  }
}

private class ShipSizeThree(x: Array[Int], y: Array[Int]) extends Ship {


  override def size: Int = 3

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) then throwReturn(true)
    }
    false
  }
}

private class ShipSizeFour(x: Array[Int], y: Array[Int]) extends Ship {

  override def size: Int = 4

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) then throwReturn(true)
    }
    false
  }
}

private class ShipSizeFive(x: Array[Int], y: Array[Int]) extends Ship {

  override def size: Int = 5

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    for (a <- 0 until this.size) {
      if (x(a) == X && y(a) == Y) then throwReturn(true)
    }
    false
  }
}

object Ship {

  def apply(x: Array[Int], y: Array[Int], size: Int): Ship = {
    size match {
      case 2 => new ShipSizeTwo(x, y)
      case 3 => new ShipSizeThree(x, y)
      case 4 => new ShipSizeFour(x, y)
      case 5 => new ShipSizeFive(x, y)
    }
  }

}

