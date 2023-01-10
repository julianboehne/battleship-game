package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.model.state.PlayerState
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
    add(player, BorderPanel.Position.Center)

  }



  val player = new Label {
    text = controller.state.playerName
    font = new Font("Serif", 0, 22)
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
      case controller.player1 => this.background = new Color(151, 164, 222)
      case controller.player2 => this.background = new Color(151, 222, 178)

    listenTo(mouse.clicks)
    listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>
        player.foreground = new Color(0, 0, 0)
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
              player.text = "invalid Ship Position"
              player.foreground = new Color(217, 113, 113)

            } else {
              controller.set(controller.getX(pos1), controller.getY(pos1), controller.getX(pos2), controller.getY(pos2))
              println("Ship implemented")
              player.text = "Ship implemented"
              player.foreground = new Color(127, 224, 126)
            }
          )
          if (e.isFailure) {
            println("invalid Ship Position")
            player.text = "invalid Ship Position"
            player.foreground = new Color(217, 113, 113)
          }

          button.text = pos2
          button1.text = pos1

          pos1 = ""
          pos2 = ""
          if (!controller.state.grid.getShips().shipCountValid()) {
            println("All ships done")
            if (controller.state == controller.player1) gui.changeToShipPanel2()
            else gui.changeToShotPanel()
          }

        } else println("Error")



    }
