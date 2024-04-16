package aview

//TODO
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.model.gridImpl.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
class TUISpec extends AnyWordSpec {
  val grid: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

  val controller = new Controller(grid)
  val tui = new TUI(controller)
  "TUI" should {
    "have a checkFired function" in {
      assert(!tui.checkFired("a1"))
    }
    "have a addShotInput function" in {
      assert(tui.addShotInput("a1") == 0)
      assert(tui.addShotInput("a1") == 1)
      assert(tui.addShotInput("z19") == 1)
    }
  }
}

