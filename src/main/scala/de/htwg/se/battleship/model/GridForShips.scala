package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class GridForShips(size: Int, ships: ShipContainer) {

  val width = 4

  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline

  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def vertical(width: Int, count: Int, x: Int): String =
    ("|" + " " * width) * (x - 1) + ("|" + "  # ") + ("|" + " " * width) * (count - x) + "|" + nextline


  def field(width: Int, count: Int, x: Int, y: Int): String =
    (horizontal(width, count) + vertical(width, count)) * (y - 1) + (horizontal(width, count) + vertical(width, count, x)) + (horizontal(width, count) + vertical(width, count)) * (count - y) + horizontal(width, count)


  def fullField: String = loop(0)

  def loop(i: Int): String = {
    if (i == ships.shipsVector.size - 1) {
      return loopPerShip(0, i)
    }

    val str0 = loopPerShip(0, i)
    //return str0
    val index = str0.indexOf("#") // Index von mehreren bekommen ???

    val str1 = loopPerShip(0, i + 1).substring(0, index) + "#" + loopPerShip(0, i + 1).substring(index + 1)
    str1
  }


  def loopPerShip(f: Int, i: Int): String = {
    if (f == ships.shipsVector(i).size - 1) {
      val str = field(width, size, ships.shipsVector(i).getX(f), ships.shipsVector(i).getY(f))
      return str
    }
    val str0 = field(width, size, ships.shipsVector(i).getX(f), ships.shipsVector(i).getY(f))
    val index = str0.indexOf('#')

    val str1 = loopPerShip(f + 1, i).substring(0, index) + "#" + loopPerShip(f + 1, i).substring(index + 1)
    str1


  }




  override def toString: String = fullField

}
