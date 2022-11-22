package de.htwg.se.battleship.model

case class FieldView(widthX: Int, countX: Int) {

  val width: Int = widthX
  val count: Int = countX
  val field: Field = Field()
  val shots: Shot = Shot()

  def emptyField(): String = field.nextline + field.fieldPrint(width, count) + field.nextline


  def setShot(): String = {
    if (shots.size == 0) return field.nextline + emptyField()

    field.nextline + loop(0, shots)

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
