package de.htwg.se.battleship.aview

class TUI {
  def isValid(input: String): Boolean = {
    input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

  }

  def getX(input: String): Char = {

    val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

    return char.charAt(0)
  }


  def getY(input: String): Int = {

    val num = "(10)|([1-9])".r.findAllIn(input).mkString.toInt

    return num
  }
}
