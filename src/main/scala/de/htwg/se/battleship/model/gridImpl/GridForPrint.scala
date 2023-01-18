package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.*

case class GridForPrint(size: Int, grid: Vector[String]) extends GridTemplate {

  override def vertical(width: Int, count: Int, row: Int): String = {
    val width2 = width - 2
    val area = row * 10

    val x: Vector[String] = grid.slice(area,area + 10)
    var str3 = ""


    x.indices.foreach(f =>

      if (x(f).length == 1) {
        str3 = str3 + " " * (width2 / 2 + 1) + x(f) + " " * (width2 / 2) + "|"
      } else if (x(f).length == 3) {
        str3 = str3 + " " * (width2 / 2) + x(f) + " " * (width2 / 2 - 1) + "|"
      } else {
        str3 = str3 + " " * (width2/2) + x(f) + " " * (width2/2) + "|"
      }
    )

    val str0 = "|" + str3 + nextline
    str0

  }

  def fullField: String = field(width, size)

  override def field(width: Int, count: Int): String = {
    var str1 = ""
    (0 until count).foreach(i => str1 += horizontal(width, count) + vertical(width, count, i))
    val str2 = str1 + horizontal(width, count)
    str2
  }


  override def toString: String = fullField

}
