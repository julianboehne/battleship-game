package de.htwg.se.battleship.model

case class Field():

  val nextline: String = sys.props("line.separator")

  def fieldPrint(width: Int, count: Int): Int = {
    println(field(width = width, count = count))
    0
  }

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline


  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def field(width: Int, count: Int): String =
    ( horizontal(width, count) + vertical(width, count) ) * count + horizontal(width, count)

