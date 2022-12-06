package de.htwg.se.battleship.model

import scala.util.control.NonLocalReturns.*

trait Ship() {

  val size: Int

  def isHIt(x: Int, y: Int): Boolean

  def getX(index: Int): Int

  def getY(index: Int): Int

  def getVectorX(): Vector[Int]

  def getVectorY(): Vector[Int]


}

private class ShipSizeTwo(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 2

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    (0 until size).foreach(a =>
      if (x(a) == X && y(a) == Y) {
        throwReturn(true)
      }
    )
    false
  }

  override def getVectorX(): Vector[Int] = x

  override def getVectorY(): Vector[Int] = y


  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)


}

private class ShipSizeThree(x: Vector[Int], y: Vector[Int]) extends Ship() {


  val size: Int = 3

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    (0 until size).foreach(a =>
      if (x(a) == X && y(a) == Y) {
        throwReturn(true)
      }
    )
    false
  }

  override def getVectorX(): Vector[Int] = x

  override def getVectorY(): Vector[Int] = y

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)

}

private class ShipSizeFour(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 4

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    (0 until size).foreach(a =>
      if (x(a) == X && y(a) == Y) {
        throwReturn(true)
      }
    )
    false
  }

  override def getVectorX(): Vector[Int] = x

  override def getVectorY(): Vector[Int] = y

  override def getX(index: Int): Int = x(index)

  override def getY(index: Int): Int = y(index)

}

private class ShipSizeFive(x: Vector[Int], y: Vector[Int]) extends Ship() {

  val size: Int = 5

  override def isHIt(X: Int, Y: Int): Boolean = returning {
    (0 until size).foreach(a =>
      if (x(a) == X && y(a) == Y) {
        throwReturn(true)
      }
    )
    false
  }

  override def getVectorX(): Vector[Int] = x

  override def getVectorY(): Vector[Int] = y

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
