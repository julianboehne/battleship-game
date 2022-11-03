package de.htwg.se.battleship.model

case class Game() {
  def startSetup(): Int = {
    val field = Field()
    println(Console.RED + "Enemy")
    field.fieldPrint(4, 10)
    print(field.nextline)

    println(Console.BLUE + "You")
    field.fieldPrint(4, 10)
    print(Console.RESET)
    0
  }
  def setShot(x: Int, y: Int): Int = {

    printf("x-Wert: %s \ny-Wert: %d \n", x, y)

    0
  }
}
