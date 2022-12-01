package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class GridForShips(size: Int, ships: ShipContainer) extends GridTemplate {



  def vertical(width: Int, count: Int, x: Int): String =
    ("|" + " " * width) * (x - 1) + ("|" + "  # ") + ("|" + " " * width) * (count - x) + "|" + nextline


 
  def fullField: String = loop(0)

  def loop(i: Int): String = {
    if (i == ships.shipsVector.size - 1) {
      return loopPerShip(0, i)
    }

    val str0 = loopPerShip(0, i)
    //return str0


    val index = str0.indexOf("#") // first #
    val index2 = str0.indexOf('#', index + 1) // second #
    val index3 = str0.indexOf('#', index2 + 1) // third #
    val index4 = str0.indexOf('#', index3 + 1) // forth #
    val index5 = str0.indexOf('#', index4 + 1) // fifth #


    val str1 = loopPerShip(0, i + 1).substring(0, index) + "#" + loopPerShip(0, i + 1).substring(index + 1)
    val str2 = str1.substring(0, index2) + '#' + str1.substring(index2 + 1) // size 2 ship


    ships.shipsVector(i).size match
      case 2 => return str2
      case 3 => return str2.substring(0, index3) + '#' + str2.substring(index3 + 1) // size 3 ship
      case 4 =>
        val str3 = str2.substring(0, index3) + '#' + str2.substring(index3 + 1)
        return str3.substring(0, index4) + '#' + str3.substring(index4 + 1) // size 4 ship

      case 5 =>
        val str3 = str2.substring(0, index3) + '#' + str2.substring(index3 + 1)
        val str4 = str3.substring(0, index4) + '#' + str3.substring(index4 + 1)
        return str4.substring(0, index5) + '#' + str4.substring(index5 + 1) // size 5 ship

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
