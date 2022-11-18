package de.htwg.se.battleship.model

case class Shot() {
  var X: Array[Int] = new Array[Int](100)
  var Y: Array[Int] = new Array[Int](100)
  var Hit: Array[Boolean] = new Array[Boolean](100)
  var size: Int = 0

  def addShot(x: Int, y: Int, hit: Boolean): Int = {
    X(size) = x
    Y(size) = y
    Hit(size) = hit
    size = size + 1;
    0

  }

  def getX(pos: Int): Int = X(pos)

  def getY(pos: Int): Int = Y(pos)

  def getHit(pos: Int): Boolean = Hit(pos)
}
