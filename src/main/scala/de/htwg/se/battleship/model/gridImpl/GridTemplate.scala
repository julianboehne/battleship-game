package de.htwg.se.battleship.model.gridImpl

//Template
abstract class GridTemplate {
  val width = 4
  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int): String = {
    ("+" + "-" * width) * count + "+" + nextline
  }

  def vertical(width: Int, count: Int): String = {
    ("|" + " " * width) * count + "|" + nextline
  }

  // Strategy
  def vertical(width: Int, count: Int, x: Int): String = ("|" + " " * width) * count + "|" + nextline


  def field(width: Int, count: Int, x: Int, y: Int): String = {
    (horizontal(width, count) + vertical(width, count)) * (y - 1) + (horizontal(width, count) + vertical(width, count, x)) + (horizontal(width, count) + vertical(width, count)) * (count - y) + horizontal(width, count)
  }

  def fullField: String //abstract


}
