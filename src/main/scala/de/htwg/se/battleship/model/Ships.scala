package de.htwg.se.battleship.model

import scala.util.control.NonLocalReturns.{returning, throwReturn}

case class ShipContainer(
                          shipTwoCount: Int,
                          shipThreeCount: Int,
                          shipFourCount: Int,
                          shipFiveCount: Int
                        ) {

  def copy(shipTwoCount: Int = this.shipTwoCount,
           shipThreeCount: Int = this.shipThreeCount,
           shipFourCount: Int = this.shipFourCount,
           shipFiveCount: Int = this.shipFiveCount
          ) = new ShipContainer(shipTwoCount, shipThreeCount, shipFourCount, shipFiveCount)

  var count: Int = 0
  val limit: Int = shipTwoCount + shipThreeCount + shipFourCount + shipFiveCount

  val ships: Array[Ship] = new Array[Ship](limit)

  def allowed(size: Int): Boolean = {
    val container:ShipContainer = new ShipContainer(shipTwoCount, shipThreeCount, shipFourCount, shipFiveCount)
    size match {
      case 2 => if (shipTwoCount == 0) return false else container.copy(shipTwoCount = shipTwoCount - 1)
      case 3 => if (shipThreeCount == 0) return false else container.copy(shipThreeCount = shipThreeCount - 1)
      case 4 => if (shipFourCount == 0) return false else container.copy(shipFourCount = shipFourCount - 1)
      case 5 => if (shipFiveCount == 0) return false else container.copy(shipFiveCount = shipFiveCount - 1)
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

  def getSymbol: String = "O"

}

private class ShipSizeTwo(x: Array[Int], y: Array[Int]) extends Ship {

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

  override def getSymbol: String = "§"

}

private class ShipSizeThree(x: Array[Int], y: Array[Int]) extends Ship {


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

private class ShipSizeFour(x: Array[Int], y: Array[Int]) extends Ship {

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

private class ShipSizeFive(x: Array[Int], y: Array[Int]) extends Ship {

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

  override def getSymbol: String = "§"

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

