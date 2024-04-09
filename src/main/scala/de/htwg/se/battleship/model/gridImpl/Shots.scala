package de.htwg.se.battleship.model.gridImpl

case class Shots(
                  X: Vector[Int],
                  Y: Vector[Int]
                ) {

  def addShot(x: Int, y: Int): Shots = {
    assert(X.size == Y.size)
    Shots(X :+ x, Y :+ y)
  }

  def wasShot(x: Int, y: Int): Boolean = X.zip(Y).contains((x, y))

  def getX(index: Int): Int = X(index)

  def getY(index: Int): Int = Y(index)

  def getLatestX: Option[Int] = X.lastOption

  def getLatestY: Option[Int] = Y.lastOption
}