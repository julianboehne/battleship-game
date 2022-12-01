package de.htwg.se.battleship.model

class EmptyGrid(size: Int) extends GridTemplate {

  def field(width: Int, count: Int): String = {
    (horizontal(width, count) + vertical(width, count)) * count + horizontal(width, count)
  }
  //def vertical(width: Int, count: Int, x: Int): String = ("|" + " " * width) * count + "|" + nextline
  def fullField: String = field(width,size)

  override def toString: String = field(width, size)
}
