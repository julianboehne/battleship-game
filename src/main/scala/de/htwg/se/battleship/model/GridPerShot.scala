package de.htwg.se.battleship.model

import de.htwg.se.battleship.model.*

case class GridPerShot(size: Int, shots: Shots) {

  val width = 4

  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline

  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def vertical(width: Int, count: Int, x: Int): String =
    ("|" + " " * width) * (x - 1) + ("|" + "  X ") + ("|" + " " * width) * (count - x) + "|" + nextline



  def field(width: Int, count: Int, x: Int, y: Int): String =
    (horizontal(width, count) + vertical(width, count)) * (y - 1) + (horizontal(width, count) + vertical(width, count, x)) + (horizontal(width, count) + vertical(width, count)) * (count - y) + horizontal(width, count)

  def fullField: String = loop(0)


  def loop(i: Int): String = {
    /*if (i == shots.size - 1 || shots.size == 1) {
      val str = field(width, size, shots.getX(i), shots.getY(i))
      if (shots.getHit(i)) return str
      val strHit = str.replace('X', 'O')
      return strHit
    }*/
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
