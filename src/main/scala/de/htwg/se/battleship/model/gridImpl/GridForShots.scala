package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.*

case class GridForShots(size: Int, grid: Vector[String]) extends GridTemplate {

  // Strategy
  /*override def vertical(width: Int, count: Int, x: Int): String =
    ("|" + " " * width) * (x - 1) + ("|" + "  X ") + ("|" + " " * width) * (count - x) + "|" + nextline*/

  override def vertical(width: Int, count: Int, row: Int): String = {
    val width2 = width - 2
    val area = row * 10

    val x: Vector[String] = grid.slice(area,area + 10)
    var str3 = ""
    x.indices.foreach(f =>
      if (x(f).length != 2) {
        str3 = str3 + " " * (width2/2 + 1) + x(f) + " " * (width2/2) + "|"
      } else {
        str3 = str3 + " " * (width2/2) + x(f) + " " * (width2/2) + "|"
      }
    )

    val str0 = "|" + str3 + nextline
    str0

  }

  def fullField: String = field(width, size)

  override def field(width: Int, count: Int): String = {
    (horizontal(width, count) + vertical(width, count, 0)) * count + horizontal(width, count)
  }




  override def toString: String = fullField

}
