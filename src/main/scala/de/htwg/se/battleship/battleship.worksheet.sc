import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.{FieldStruture, Field, Shot}


import java.io.PrintWriter
import java.io.File

val field1: Field = Field(4, 10)
val field2: Field = Field(4, 10)

val controller: Controller = Controller(field1, field2)
controller.addShot(5, 7, field1)




val pw = new PrintWriter(new File("A:\\Dokumente\\GitHub\\battleship-game\\src\\test\\scala\\de\\htwg\\se\\battleship\\controller\\review\\toString.txt" ))
pw.write(field1.setShot()+field2.setShot())
pw.close
//print(test.startSetup())

//scala.io.Source.fromFile("A:\\Dokumente\\GitHub\\battleship-game\\src\\main\\scala\\de\\htwg\\se\\battleship\\FieldViewSpec.txt").mkString ==  test.startSetup()