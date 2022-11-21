import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.{Field, FieldView, Shot}

import java.io.*

val test: Field = Field()

test.fieldPrint(4,10)
test.updateFieldPrint(4,10,3,7)
test.updateFieldPrint(4,10,8,2)

val pw = new PrintWriter(new File("A:\\Dokumente\\GitHub\\battleship-game\\src\\test\\scala\\de\\htwg\\se\\battleship\\model\\review\\updateFieldPrint2.txt" ))
pw.write(test.updateFieldPrint(4,10,8,2))
pw.close
//print(test.startSetup())

//scala.io.Source.fromFile("A:\\Dokumente\\GitHub\\battleship-game\\src\\main\\scala\\de\\htwg\\se\\battleship\\FieldViewSpec.txt").mkString ==  test.startSetup()