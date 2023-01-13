package de.htwg.se.battleship.model.gridImpl

import scala.util.control.NonLocalReturns.*

case class Shots(
                  X: Vector[Int],
                  Y: Vector[Int]
                ) {


  def addShot(x: Int, y: Int): Shots = {
    assert(X.size == Y.size)
    Shots(X :+ x, Y :+ y)
  }

  def wasShot(x: Int, y: Int): Boolean = returning {
    X.indices.foreach(i =>
      if(X(i) == x && Y(i) == y) throwReturn(true)
    )
    false
  }
  
  def getX(index: Int): Int = X(index)

  def getY(index: Int): Int = Y(index)

  def getLatestX: Int = {
    if (X.isEmpty) return -1
    X(X.size - 1)
  }

  def getLatestY: Int = {
    if (Y.isEmpty) return -1
    Y(Y.size - 1)
  }


}
