package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.controller.state.{Player1State, Player2State, PlayerState}
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import javax.swing.text.AbstractDocument.Content
import scala.swing.*
import scala.util.Try

case class ShipPanel(controller: ControllerInterface, gui: GUI) extends Observer:


  override def update: Unit = println(controller.GridShipToString)

  def contentPanel = new BorderPanel {
    add(gui.headline, BorderPanel.Position.North) // Label-Bar

    add(new CellPanel(), BorderPanel.Position.West) // Game Field
    // Player Name
    add(player, BorderPanel.Position.Center)
    player.foreground = new Color(0, 0, 0)
    player.text = controller.state.getPlayerName

    add(info, BorderPanel.Position.East)
    info.foreground = new Color(0, 0, 0)
    info.text = "Please try to implement your Ships!"


  }



  val player: Label = new Label {
    text = controller.state.getPlayerName
    font = new Font("Sans Serif", 0, 22)
  }

  val info: Label = new Label {
    text = "Please try to implement your Ships!"
    font = new Font("Sans Serif", 0, 22)
  }

  class CellPanel() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField

    def printField =
      controller.state.board.map(x => contents += new CellButton(x))

    def test(): Unit = {

    }

  var pos1 = ""
  var pos2 = ""
  var button1: Button = new Button()

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
          button.text = "start"
          pos1 = pos
          button1 = this
        } else if (!pos1.equals("") && pos2.equals("")) {
          button.text = "end"
          pos2 = pos
          val e = Try(

            if (!controller.checkShip(controller.getX(pos1), controller.getY(pos1), controller.getX(pos2), controller.getY(pos2))) {
              println("invalid Ship Position")
              info.text = "invalid Ship Position"
              info.foreground = new Color(217, 113, 113)

            } else {
              controller.set(controller.getX(pos1), controller.getY(pos1), controller.getX(pos2), controller.getY(pos2))
              println("Ship implemented")
              info.text = "Ship implemented"
              info.foreground = new Color(127, 224, 126)
            }
          )
          if (e.isFailure) {
            println("invalid Ship Position")
            info.text = "invalid Ship Position"
            info.foreground = new Color(217, 113, 113)
          }

          button.text = pos2
          button1.text = pos1

          pos1 = ""
          pos2 = ""
          if (!controller.state.grid.getShips().shipCountValid()) {
            println("All ships done")
            if (controller.state == controller.player1)gui.changeToShipPanel2()
            else gui.changeToShotPanel()
          }

        } else println("Error")



    }
