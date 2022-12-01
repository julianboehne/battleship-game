package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class GridForShots(size: Int, shots: Shots) extends GridTemplate {

  def vertical(width: Int, count: Int, x: Int): String =
    ("|" + " " * width) * (x - 1) + ("|" + "  X ") + ("|" + " " * width) * (count - x) + "|" + nextline

  def fullField: String = loop(0)


  def loop(i: Int): String = {
    if (i == shots.X.size - 1 || shots.X.size == 1) {
      val str = field(width, size, shots.getX(i), shots.getY(i))
      if (shots.getHit(i)) return str
      val strHit = str.replace('X', 'O')
      return strHit
    }
    val str0 = field(width, size, shots.getX(i), shots.getY(i))
    val index = str0.indexOf('X')

    if (shots.getHit(i)) {
      val str1 = loop(i + 1).substring(0, index) + "X" + loop(i + 1).substring(index + 1)
      return str1
    }
    val str1 = loop(i + 1).substring(0, index) + "O" + loop(i + 1).substring(index + 1)
    str1

  }

  override def toString: String = fullField

}
