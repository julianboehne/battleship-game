package de.htwg.se.battleship.model

trait Ship() {

  def size: Int
  def isHIt(x: Int, y: Int): Boolean
  var x: Array[Int] = Array(0)
  var y: Array[Int] = Array(0)

}

private class ShipSizeTwo(x: Int, y: Int, direction: Int) extends Ship {
  this.x = Array(2)
  this.y = Array(2)
  override def size: Int = 2
  override def isHIt(x: Int, y: Int): Boolean = {
    true
  }
}

private class ShipSizeThree(x: Int, y: Int, direction: Int) extends Ship {
  this.x = Array(3)
  this.y = Array(3)

  override def size: Int = 3
  override def isHIt(x: Int, y: Int): Boolean = {
    false
  }
}

private class ShipSizeFour(x: Int, y: Int, direction: Int) extends Ship {
  this.x = Array(4)
  this.y = Array(4)
  override def size: Int = 4
  override def isHIt(x: Int, y: Int): Boolean = {
    val a = true
    false
  }
}

private class ShipSizeFive(x: Int, y: Int, direction: Int) extends Ship {
  this.x = Array(5)
  this.y = Array(5)

  override def size: Int = 5
  override def isHIt(x: Int, y: Int): Boolean = {
    val a = false
    false
  }
}

object Ship {

  def apply(x: Int, y: Int, number: Int, direction: Int): Ship = {
    number match {
      case 2 => new ShipSizeTwo(x, y, direction)
      case 3 => new ShipSizeThree(x, y, direction)
      case 4 => new ShipSizeFour(x, y, direction)
      case 5 => new ShipSizeFive(x, y, direction)
    }
  }

}

val f:Ship = Ship(5,5,5,5)