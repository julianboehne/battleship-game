package de.htwg.se.battleship.model

case class FieldView(widthX: Int, countX: Int) {

  val width: Int = widthX
  val count: Int = countX
  val field: Field = Field()

  def startSetup(): String = {
    val str0 = s"${Console.RED}Player1 ${field.nextline}"
    val str1 = str0 + field.fieldPrint(width, count) + field.nextline

    val str2 = str1 + s"${Console.BLUE}Player2 ${field.nextline}"
    val str3 = str2 + field.fieldPrint(width, count) + field.nextline + Console.RESET
    str3
  }


  def setShot(shots: Shot): String = {
    //val str0 = s"x-Wert: $x \ny-Wert: $y" + field.nextline
    //val str1 = str0 + "Shot" + field.nextline
    loop(0, shots)

  }

  def loop(i: Int, shots: Shot): String = {
    if (i == shots.size - 1 || shots.size == 1) {
      val str = field.updateFieldPrint(width, count, shots.getX(i), shots.getY(i))
      if (shots.getHit(i)) return str
      val strHit = str.replace('X', 'O')
      return strHit
    }
    val str0 = field.updateFieldPrint(width, count, shots.getX(i), shots.getY(i))
    val index = str0.indexOf('X')

    if (shots.getHit(i)) {
      val str1 = loop(i + 1, shots).substring(0, index) + "X" + loop(i + 1, shots).substring(index + 1)
      return str1
    }
    val str1 = loop(i + 1, shots).substring(0, index) + "O" + loop(i + 1, shots).substring(index + 1)
    str1
  }

}
