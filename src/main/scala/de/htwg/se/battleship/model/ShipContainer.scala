package de.htwg.se.battleship.model


case class ShipContainer(shipsVector: Vector[Ship]) {
  def addShip(ship: Ship): ShipContainer = ShipContainer(shipsVector :+ ship)

  //def getShip(index:Int) : Ship = shipsVector(index)

  def isValid(x1: Int, y1: Int, x2: Int, y2: Int): Boolean ={
    println(s"x1: $x1, y1: $y1, x2: $x2, y2: $y2")
    if((x1 == x2 && y1 != y2) || (x1 != x2 && y1 == y2)) return true
    false
  }

  def getShip(x1: Int, y1: Int, x2: Int, y2: Int): Ship = {
    if (x1 == x2 && y1 != y2) {
      val size = Math.abs(y2 - y1) + 1

      val x: Vector[Int] = ((1 to size).map(x => x1)).toVector
      val y: Vector[Int] = (y1 to y2).toVector

      Ship(x, y, size)

    } else {
      val size = Math.abs(x2 - x1) + 1

      val x: Vector[Int] = (x1 to x2).toVector
      val y: Vector[Int] = ((1 to size).map(y => y1)).toVector

      Ship(x, y, size)


    }
  }

}
