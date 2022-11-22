package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.{Field, Player}
import aview.*
import controller.*


object Battleship {
  val field1: Field = Field(4, 10)
  val field2: Field = Field(4, 10)
  val controller: Controller = Controller(field1, field2)
  val input: TUI = TUI(controller)


  def main(args: Array[String]): Unit = {
    print("Welcome to Battleship-Game")
    input.setup()
    print(s"${Console.CYAN}Player 1:${Console.RESET} Name eingeben: ${Console.BOLD}")
    val player1 = readLine()
    controller.field1.player = Player(player1)

    print(s"${Console.CYAN}Player 2:${Console.RESET} Name eingeben: ${Console.BOLD}")
    val player2 = readLine()
    controller.field2.player = Player(player2)


    print(s"${Console.CYAN}Player X:${Console.RESET} You have to add X battleships\n")
    print(s"${Console.BLUE}Ships:${Console.RESET} \n")
    print(s"Ship 2: X, Ship 3: X, Ship 4: X, Ship 5: X  \n")





    while (true) {
      print(s"${Console.CYAN}Player ${controller.getField().player.toString}:${Console.RESET} Shot(ex. H5): ${Console.BOLD}")
      //Eingabe
      val line = readLine()
      //Exit
      if (line == "exit" || line == "1") {
        return
      }
      print(s"${Console.RESET}")
      input.run(line)
    }

  }


}