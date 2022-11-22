package de.htwg.se.battleship.model

case class Field(widthX: Int, countX: Int) {

  val width: Int = widthX
  val count: Int = countX
  val field: FieldStruture = FieldStruture()
  val shots: Shot = Shot()
  var player: Player = Player("Name")
  def emptyField(): String = field.nextline + field.fieldPrint(width, count) + field.nextline


  def setShot(): String = {
    if (shots.size == 0) return field.nextline + emptyField()

    field.nextline + loop(0)

  }

  def loop(i: Int): String = {
    if (i == shots.size - 1 || shots.size == 1) {
      val str = field.updateFieldPrint(width, count, shots.getX(i), shots.getY(i))
      if (shots.getHit(i)) return str
      val strHit = str.replace('X', 'O')
      return strHit
    }
    val str0 = field.updateFieldPrint(width, count, shots.getX(i), shots.getY(i))
    val index = str0.indexOf('X')

    if (shots.getHit(i)) {
      val str1 = loop(i + 1).substring(0, index) + "X" + loop(i + 1).substring(index + 1)
      return str1
    }
    val str1 = loop(i + 1).substring(0, index) + "O" + loop(i + 1).substring(index + 1)
    str1
  }

}
