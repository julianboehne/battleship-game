package model.gridImpl

import model.*

case class GridForPrint(size: Int, grid: Vector[String]) extends GridTemplate {

  override def vertical(width: Int, count: Int, row: Int): String = {
    val width2 = width - 2
    val area = row * count

    val x: Vector[String] = grid.slice(area, area + count)

    val str3 = x.indices.map { f =>
      x(f).length match {
        case 1 => " " * (width2 / 2 + 1) + x(f) + " " * (width2 / 2) + "|"
        case 3 => " " * (width2 / 2) + x(f) + " " * (width2 / 2 - 1) + "|"
        case _ => " " * (width2 / 2) + x(f) + " " * (width2 / 2) + "|"
      }
    }.mkString

    "|" + str3 + nextline
  }

  def fullField: String = field(width, size)

  override def field(width: Int, count: Int): String = {
    (0 until count).map(i => horizontal(width, count) + vertical(width, count, i)).mkString + horizontal(width, count)
  }


  override def toString: String = fullField

}
