package de.htwg.se.battleship

import model.Field

object Battleship {

  def main(args: Array[String]): Unit = {
    val field = new Field()
    field.fieldPrint(4, 10)

  }


}