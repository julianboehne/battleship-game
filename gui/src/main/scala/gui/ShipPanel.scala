package gui

import controller.*
import util.GameState.*
import util.Observer
import util.state.*

import javax.management.Notification
import javax.swing.border.EmptyBorder
import javax.swing.text.AbstractDocument.Content
import scala.swing.*
import scala.swing.event.ButtonClicked
import scala.util.Try

case class ShipPanel(controller: ControllerInterface, gui: GUI) extends Observer:


  override def update: Unit = gui.update

  def contentPanel: BorderPanel = new BorderPanel {
    add(gui.headline, BorderPanel.Position.North) // Label-Bar

    add(new CellPanel(), BorderPanel.Position.West) // Game Field
    // Player Name
    add(player, BorderPanel.Position.Center)
    player.foreground = new Color(0, 0, 0)
    player.text = controller.state.getPlayerName + ": " + controller.GameStateText

    add(info, BorderPanel.Position.South)
    //info.foreground = new Color(0, 0, 0)
    //info.text = "Please try to implement your Ships!"


  }


  val player: Label = new Label {
    //text = controller.state.getPlayerName + ": " + controller.GameStateText
    font = new Font("Sans Serif", 0, 22)
  }

  private val info: Label = new Label {
    text = controller.GameStateText
    font = new Font("Sans Serif", 0, 22)
  }

  private class CellPanel extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField

    private def printField =
      controller.state.getShipBoard.map(x =>
        if (x.equals("#")) contents += new CellButton("\uD83D\uDEA2")
        else contents += new CellButton(x)

      )
    //controller.state.board.map(x => contents += new CellButton(x))

    def test(): Unit = {

    }

  private var pos1 = ""
  private var pos2 = ""
  private var button1: Button = new Button()

  class CellButton(pos: String) extends Button(pos):
    //this.background = new Color(151, 164, 222)

    controller.state match
      case _: Player1State => this.background = new Color(151, 164, 222)
      case _: Player2State => this.background = new Color(151, 222, 178)

    listenTo(mouse.clicks)
    listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>
        info.foreground = new Color(0, 0, 0)
        if (pos1.equals("") && pos2.equals("")) {
          button.text = "\uD83D\uDEA2"
          pos1 = pos
          button1 = this
        } else if (!pos1.equals("") && pos2.equals("")) {
          pos2 = pos
          val e = Try(

            if (!controller.checkShip(controller.getX(pos1), controller.getY(pos1), controller.getX(pos2), controller.getY(pos2))) {
              println("invalid Ship Position")
              info.text = "invalid Ship Position"
              info.foreground = new Color(194, 0, 0)

            } else {
              controller.set(controller.getX(pos1), controller.getY(pos1), controller.getX(pos2), controller.getY(pos2))
              if (!controller.state.grid.ships.shipSingleCountValid()) {
                controller.undo()
                println("Too many ships with this size")
                info.text = "Too many ships with this size"
                info.foreground = new Color(194, 0, 0)
              } else if (!controller.state.grid.ships.shipPosition()) {
                controller.undo()
                println("You already place a ship at this position!")
                info.text = "You already place a ship at this position!"
                info.foreground = new Color(194, 0, 0)
              } else {
                println("Ship implemented")
                info.text = "Ship implemented"
                info.foreground = new Color(2, 171, 16)
              }
            }
          )
          if (e.isFailure) {
            println("invalid Ship Position")
            info.text = "invalid Ship Position"
            info.foreground = new Color(194, 0, 0)
          }

          button.text = pos2
          button1.text = pos1

          pos1 = ""
          pos2 = ""
          if (!controller.state.grid.ships.shipCountValid()) {
            println(controller.GridShipToString)
            println("All ships done")

            controller.gameState match
              case SHIP_PLAYER1 => controller.gameState = SHIP_PLAYER2
              case SHIP_PLAYER2 => controller.gameState = SHOTS

            //update

          }
          update


        } else println("Error")


    }
