package de.htwg.se.battleship

import scala.io.StdIn.readLine
import model.FieldView
import aview.*
import controller.*


object Battleship {
  val field1: FieldView = FieldView(4, 10)
  val field2: FieldView = FieldView(4, 10)
  val controller: Controller = Controller(field1, field2)
  val input: TUI = TUI(controller)


  def main(args: Array[String]): Unit = {
    print("Welcome to Battleship-Game")

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