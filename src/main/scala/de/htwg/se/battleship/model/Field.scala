package de.htwg.se.battleship.model

case class Field():

  val nextline: String = sys.props("line.separator")

  def fieldPrint(width: Int, count: Int): String = {
    field(width = width, count = count)
  }

  def updateFieldPrint(width: Int, count: Int, x: Int, y: Int): String = {
    updateField(width, count, x, y)
  }

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline


  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def vertical(width: Int, count: Int, x: Int): String = {
    ("|" + " " * width) * (x - 1) + ("|" + "  X ") + ("|" + " " * width) * (count - x) + "|" + nextline
  }

  def field(width: Int, count: Int): String =
    (horizontal(width, count) + vertical(width, count)) * count + horizontal(width, count)

  def updateField(width: Int, count: Int, x: Int, y: Int): String =
    (horizontal(width, count) + vertical(width, count)) * (y - 1) + (horizontal(width, count) + vertical(width, count, x)) + (horizontal(width, count) + vertical(width, count)) * (count - y) + horizontal(width, count)