package de.htwg.se.battleship.model
//TODO
import de.htwg.se.battleship.model.gridImpl.GridForPrint
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GridForPrintSpec extends AnyWordSpec {

  val test = GridForPrint(4,Vector("A1","B1","C1","A2","B2","C2","A3","B3","C3"))

    "GridForPrint" should {

      /*"have a field function" in {
        println(test.field(4,1))
        test.field(1,1) should be("rofl")
      }*/

      val nextline: String = sys.props("line.separator")

      "correctly implement the vertical function" in {
        val grid = Vector("A1", "B1", "C1", "A2", "B2", "C2", "A3", "B3", "C3")
        val gridForShots = GridForPrint(3, grid)
        gridForShots.vertical(1, 3, 0) should be("|A1|B1|C1|" + nextline)
      }

      /*   "correctly implement the field function" in {
           val grid = Vector("A1","B1","C1","A2","B2","C2","A3","B3","C3")
           val gridForShots = GridForPrint(3, grid)
           println( gridForShots.field(4, 3) )
           gridForShots.field(4, 3) should be("")
         }*/


      }




  }

