package de.htwg.se.battleship.model

// x, y (example: A3)
case class ShootPosition(x: Char, y: Int) {
  def getPosition: String = x.toString + y.toString
}