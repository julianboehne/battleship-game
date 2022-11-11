package de.htwg.se.battleship.model

case class Shot() {
  var X: Array[Int] = new Array[Int](100)
  var Y: Array[Int] = new Array[Int](100)
  var size: Int = 1

  def addShot(x: Int, y: Int): Int = {
    X(size) = x
    Y(size) = y
    0

  }

  def getX(pos: Int): Int = X(pos)

  def getY(pos: Int): Int = Y(pos)
}
