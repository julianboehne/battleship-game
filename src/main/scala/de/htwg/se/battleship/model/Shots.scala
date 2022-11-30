package de.htwg.se.battleship.model


case class Shots(
                  X: Vector[Int],
                  Y: Vector[Int]
                ) {


  def addShot(x: Int, y: Int): Shots = {
    assert(X.size != Y.size)
    val vectorXnew = Vector(x)
    val vectorYnew = Vector(y)
    new Shots(X ++ vectorXnew, Y ++ vectorYnew)

  }

  def getX(index: Int): Int = X(index)

  def getY(index: Int): Int = Y(index)

  def getHit(i: Int): Boolean = {
    true
  }


}
