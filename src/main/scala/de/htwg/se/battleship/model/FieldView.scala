package de.htwg.se.battleship.model

case class FieldView(widthX: Int, countX: Int) {
  
  val width: Int = widthX
  val count: Int = countX
  
  def startSetup(): Int = {
    val field = Field()
    println(Console.RED + "Enemy")
    field.fieldPrint(width, count)
    print(field.nextline)

    println(Console.BLUE + "You")
    field.fieldPrint(width, count)
    print(Console.RESET)
    0
  }


  def setShot(x: Int, y: Int): Int = {

    println(s"x-Wert: $x \ny-Wert: $y")

    val field = Field()
    println(Console.GREEN + "Shot Test")
    field.updateFieldPrint(width, count, x, y)
    print(field.nextline)
    print(Console.RESET)


    0
  }
}
