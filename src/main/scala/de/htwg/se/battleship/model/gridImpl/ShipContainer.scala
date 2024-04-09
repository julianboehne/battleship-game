package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.gridImpl.Ship

case class ShipContainer(shipsVector: Vector[Ship]) {
  def addShip(ship: Ship): ShipContainer = {
    ShipContainer(shipsVector :+ ship)
  }

  def getSize: Int = shipsVector.size

  def isValid(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    (x1 == x2 && y1 != y2) || (x1 != x2 && y1 == y2)
  }

  def getShip(x1: Int, y1: Int, x2: Int, y2: Int): Ship = {
    val size = Math.abs(if (x1 == x2) y2 - y1 else x2 - x1) + 1
    val x: Vector[Int] = if (x1 == x2) Vector.fill(size)(x1) else if (x1 > x2) (x1 to x2 by -1).toVector else (x1 to x2).toVector
    val y: Vector[Int] = if (y1 == y2) Vector.fill(size)(y1) else if (y1 > y2) (y1 to y2 by -1).toVector else (y1 to y2).toVector
    Ship(x, y, size)
  }

  def removeShip(): ShipContainer = ShipContainer(shipsVector.dropRight(1))

  def shipCountValid(): Boolean = shipsVector.size != 8

  def shipSingleCountValid(): Boolean = {
    val sizeVec = shipsVector.map(_.size)
    val counts = sizeVec.groupBy(identity).mapValues(_.size)
    counts.getOrElse(2, 0) <= 3 && counts.getOrElse(3, 0) <= 2 && counts.getOrElse(4, 0) <= 2 && counts.getOrElse(5, 0) <= 1
  }

  def isHit(x: Int, y: Int): Boolean = shipsVector.exists(_.isHit(x, y))

  def shipPosition(): Boolean = {
    !shipsVector.combinations(2).exists {
      case Vector(ship1, ship2) =>
        (0 until ship1.size).exists(i => (0 until ship2.size).exists(j => ship1.getX(i) == ship2.getX(j) && ship1.getY(i) == ship2.getY(j)))
      case _ => false
    }
  }
}