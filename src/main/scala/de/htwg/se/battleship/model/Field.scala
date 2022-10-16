package de.htwg.se.battleship.model

class Field(var width: Int, var count: Int):


  def fieldPrint(): Int = {
    println(Console.RED + "Enemy")
    println(field(width = 4, count = 10))
    print(nextline)
    println(Console.BLUE + "You")
    println(field(width = 4, count = 10))
    print(Console.RESET)

    return 0

  }

  

  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int): String =
    ("+" + "-" * width) * count + "+" + nextline


  def vertical(width: Int, count: Int): String =
    ("|" + " " * width) * count + "|" + nextline

  def field(width: Int, count: Int): String =
    ( horizontal(width, count) + vertical(width, count) ) * count + horizontal(width, count)

