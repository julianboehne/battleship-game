package util

import core.util.Command
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class incrCommand extends Command {
  var state:Int =0
  override def doStep: Unit = state+=1

  override def undoStep: Unit = state-=1

  override def redoStep: Unit = state+=1
}

class CommandSpec extends AnyWordSpec {
  "A Command" should {

    "have a do step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.doStep
      command.state should be(2)

    }
    "have an undo step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.undoStep
      command.state should be(0)
    }
    "have a redo step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.undoStep
      command.state should be(0)
      command.redoStep
      command.state should be(1)
    }
  }
}