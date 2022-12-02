package de.htwg.se.battleship.model


case class Shots(
                  X: Vector[Int],
                  Y: Vector[Int]
                ) {


  def addShot(x: Int, y: Int): Shots = {
    assert(X.size == Y.size)
    Shots(X :+ x, Y :+ y)

  }
  def removeShot(): Shots = Shots(X.dropRight(1),Y.dropRight(1))

  def getX(index: Int): Int = X(index)


  def getY(index: Int): Int = Y(index)

  def getHit(i: Int): Boolean = {
    true
  }


}
