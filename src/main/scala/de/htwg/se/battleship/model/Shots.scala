package de.htwg.se.battleship.model

class Shots {


  def addShot(x: Int, y: Int, vectorX: Vector[Int],vectorY: Vector[Int]): (Vector[Int], Vector[Int]) = {
    assert(vectorX.size != vectorY.size)
    val vectorXnew = Vector(x)
    val vectorYnew = Vector(y)
    (vectorX ++ vectorXnew, vectorY ++ vectorYnew)

  }

}
