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
    val player1 = readLine(s"${Console.CYAN}Player 1:${Console.RESET} Name eingeben: ${Console.BOLD}")
    controller.field1.player = Player(player1)

    val player2 = readLine(s"${Console.RESET}${Console.CYAN}Player 2:${Console.RESET} Name eingeben: ${Console.BOLD}")
    controller.field2.player = Player(player2)




    print(s"${Console.CYAN}Player ${controller.getField.player}:${Console.RESET} You have to add following battleships\n")
    while(!controller.getField.allShipsImplemented()) {
      print("\n")
      input.shipCountPrint()

      val schiffStartpunkt = readLine("Schiffstartpunkt: ")
      val schiffsEndpunkt = readLine("Schiffendwert: ")

      input.addShip(schiffStartpunkt, schiffsEndpunkt)


    }
    println("Alle implementiert\n")
    controller.setField()
    //Player2 Ships



    controller.setField()
    while (true) {
      print(s"${Console.CYAN}Player ${controller.getField.player}:${Console.RESET} Shot(ex. H5): ${Console.BOLD}")
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