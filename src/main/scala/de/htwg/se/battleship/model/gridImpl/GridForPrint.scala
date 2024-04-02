package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.*

case class GridForPrint(size: Int, grid: Vector[String]) extends GridTemplate {

  override def vertical(width: Int, count: Int, row: Int): String = {
    val width2 = width - 2
    val area = row * count

    val x: Vector[String] = grid.slice(area, area + count)

    val str3 = x.indices.foldLeft("") { (acc, f) =>
      if (x(f).length == 1) {
        acc + " " * (width2 / 2 + 1) + x(f) + " " * (width2 / 2) + "|"
      } else if (x(f).length == 3) {
        acc + " " * (width2 / 2) + x(f) + " " * (width2 / 2 - 1) + "|"
      } else {
        acc + " " * (width2 / 2) + x(f) + " " * (width2 / 2) + "|"
      }
    }

    val str0 = "|" + str3 + nextline
    str0
  }

  def fullField: String = field(width, size)

  override def field(width: Int, count: Int): String = {
    val str1 = (0 until count).map(i => horizontal(width, count) + vertical(width, count, i)).mkString
    val str2 = str1 + horizontal(width, count)
    str2
  }


  override def toString: String = fullField

}
