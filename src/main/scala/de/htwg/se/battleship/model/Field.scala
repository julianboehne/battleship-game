package de.htwg.se.battleship.model

case class Field(widthX: Int, countX: Int) {

  val width: Int = widthX
  val count: Int = countX

  var player: Player = Player("Name")
  val field: FieldStruture = FieldStruture()
  val shots: Shot = Shot()
  val ships: ShipContainer = new ShipContainer(1,1,1,1)


  def setShot(): String = {
    if (shots.size == 0) return field.nextline + emptyField()
    field.nextline + loop(0)
  }

  def emptyField(): String = field.nextline + field.fieldPrint(width, count) + field.nextline

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

  def allShipsImplemented(): Boolean = ships.isDone

  def addNewShip(x1: Int, y1: Int, x2: Int, y2: Int): Int = {
    if (x1 == x2 && y1 != y2) {
      // vertical
      val size = Math.abs(y2 - y1) + 1
      val x: Array[Int] = new Array[Int](size)
      val y: Array[Int] = new Array[Int](size)

      var i = 0
      for (a <- y1 to y2) {
        x(i) = x1
        y(i) = a
        i += 1
      }
      if (ships.addShip(x, y, size) == 1) return 1
      return 0

    } else if (x1 != x2 && y1 == y2) {
      // horizontal
      val size = Math.abs(x2 - x1) + 1
      val x: Array[Int] = new Array[Int](size)
      val y: Array[Int] = new Array[Int](size)

      var i = 0
      for (a <- x1 to x2) {
        x(i) = a
        y(i) = y1
        i += 1
      }
      if (ships.addShip(x, y, size) == 1) return 1
      return 0
    }
    1 // invalid
  }


}
