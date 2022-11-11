package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.FieldView
import aview.*
import controller.*


object Battleship {
  val field: FieldView = FieldView(4, 10)
  val controller: Controller = Controller(field)
  val input: TUI = TUI(controller)


  def main(args: Array[String]): Unit = {
    println("Welcome to Battleship-Game")

    input.setup()
    while (true) {
      print(s"Shot(ex. H5): ${Console.CYAN}")
      //Eingabe
      val line = readLine()
      //Exit
      if (line == "exit" || line == "1") {
        return
      }
      input.run(line)
    }

  }


}