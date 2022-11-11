package de.htwg.se.battleship.model

case class FieldView(widthX: Int, countX: Int) {
  
  val width: Int = widthX
  val count: Int = countX
  
  def startSetup(): String = {
    val field = Field()
    val str0 = s"${Console.RED} Enemy ${field.nextline}"
    val str1 = str0 + field.fieldPrint(width, count) + field.nextline

    val str2 = str1 + s"${Console.BLUE} Enemy ${field.nextline}"
    val str3 = str2 + field.fieldPrint(width, count) + field.nextline + Console.RESET
    str3
  }


  def setShot(x: Int, y: Int): String = {

    val field = Field()

    val str0 = s"x-Wert: $x \ny-Wert: $y" + field.nextline
    val str1 = str0 + "Shot Test" + field.nextline
    val str2 = str1 + field.updateFieldPrint(width, count, x, y) + field.nextline
    str2

  }
}
