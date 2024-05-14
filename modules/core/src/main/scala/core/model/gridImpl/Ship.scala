package core.model.gridImpl

import core.model.*

import scala.util.control.NonLocalReturns.*

trait Ship {
  val x: Vector[Int]
  val y: Vector[Int]
  val size: Int

  def isHit(X: Int, Y: Int): Boolean = x.lazyZip(y).exists { case (xi, yi) => xi == X && yi == Y }

  def getVectorX: Vector[Int] = x

  def getVectorY: Vector[Int] = y

  def getX(index: Int): Int = x(index)

  def getY(index: Int): Int = y(index)
}

private class ShipSize(override val size: Int, override val x: Vector[Int], override val y: Vector[Int]) extends Ship

object Ship {
  def apply(x: Vector[Int], y: Vector[Int], size: Int): Ship = new ShipSize(size, x, y)
}
