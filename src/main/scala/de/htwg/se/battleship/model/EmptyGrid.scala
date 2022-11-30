package de.htwg.se.battleship.model

case class EmptyGrid(size: Int) {

  val width = 4

  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline


  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def field(width: Int, count: Int): String =
    (horizontal(width, count) + vertical(width, count)) * count + horizontal(width, count)

  override def toString: String = field(width, size)
}
