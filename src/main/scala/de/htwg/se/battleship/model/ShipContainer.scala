package de.htwg.se.battleship.model


case class ShipContainer(shipsVector: Vector[Ship]) {
  def addShip(ship: Ship): ShipContainer = {
    ShipContainer(shipsVector :+ ship)

  }

  //def getShip(index:Int) : Ship = shipsVector(index)

  def isValid(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    if ((x1 == x2 && y1 != y2) || (x1 != x2 && y1 == y2)) return true
    false
  }

  def getShip(x1: Int, y1: Int, x2: Int, y2: Int): Ship = {
    if (x1 == x2 && y1 != y2) {
      val size = Math.abs(y2 - y1) + 1
      val x: Vector[Int] = ((1 to size).map(x => x1)).toVector

      if (y1 > y2) {
        val y: Vector[Int] = (y1 to y2 by -1).toVector
        Ship(x, y, size)

      } else {
        val y: Vector[Int] = (y1 to y2).toVector
        Ship(x, y, size)
      }


    } else {
      val size = Math.abs(x2 - x1) + 1
      val y: Vector[Int] = ((1 to size).map(y => y1)).toVector
      if (x1 > x2) {
        val x: Vector[Int] = (x1 to x2 by -1).toVector
        Ship(x, y, size)

      } else {
        val x: Vector[Int] = (x1 to x2).toVector
        Ship(x, y, size)

      }


    }

  }

  def removeShip(): ShipContainer = ShipContainer(shipsVector.dropRight(1))

  def shipCountValid(): Boolean = {
    if (shipsVector.size == 8) false
    else true
  }

  def shipSingleCountValid(): Boolean = {
    val sizeVec = (0 until shipsVector.size).map(x => shipsVector(x).size).toVector
    val zweierCount = sizeVec.count(x => {
      x == 2
    })
    val dreierCount = sizeVec.count(x => {
      x == 3
    })
    val viererCount = sizeVec.count(x => {
      x == 4
    })
    val fuenferCount = sizeVec.count(x => {
      x == 5
    })

    if (zweierCount > 3) false
    else if (dreierCount > 2) false
    else if (viererCount > 2) false
    else if (fuenferCount > 1) false
    else true

  }

  def isHit(x: Int, y: Int): Boolean = {

    (0 until shipsVector.size).map(i =>
      if (shipsVector(i).isHIt(x, y)) return true
    )
    false
  }

  def shipPosition(): Boolean = {
    shipsVector.indices.map(x => {
      shipsVector.indices.map(y => {
        (0 until (shipsVector(x).size)).map(k => {
          (0 until (shipsVector(y).size)).map(h =>
            if (shipsVector(x).getX(k) == shipsVector(y).getX(h) && shipsVector(x).getY(k) == shipsVector(y).getY(h) && x != y) return false
          )
        })
      })
    })
    true

  }


}
