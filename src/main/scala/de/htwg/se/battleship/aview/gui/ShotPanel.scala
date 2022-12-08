package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.model.state.PlayerState
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import javax.swing.text.AbstractDocument.Content
import scala.swing.*

case class ShotPanel(controller: Controller, gui: GUI) extends Observer:


  override def update: Unit = println(controller.GridShipToString)

  def contentPanel = new BorderPanel {
    controller.state = controller.player1
    add(new Label("Battleship Game"), BorderPanel.Position.North) // Label-Bar
    add(new CellPanel(), BorderPanel.Position.West) // Game Field

    add(new Label("Shot"), BorderPanel.Position.South)

    controller.changeState()
    add(new CellPanel(), BorderPanel.Position.East)
  }

  class CellPanel() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField

    def printField =
      controller.state.grid.board.map(x => contents += new CellButton(x, controller.state))


  class CellButton(pos: String, player: PlayerState) extends Button(pos):
    player match
      case controller.player1 => this.background = new Color(151, 164, 222)
      case controller.player2 => this.background = new Color(151, 222, 178)
    listenTo(mouse.clicks)
    //listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>
        if (button.text.equals("1") || button.text.equals("2") || button.text.equals("X")) println("You already fired there2")
        else {
          if (player == controller.player1) button.text = "1"
          else if(player == controller.player2) button.text = "2"
          else button.text = "fatal error"

          val x = controller.getX(pos)
          val y = controller.getY(pos)
          if (controller.alreadyFired(x, y)) {
            println("You already fired there")
            //contents = testPanel

          } else {
            controller.state = player
            controller.addShot(x, y)
            if (player.grid.ships.isHit(x, y)) button.text = "X"

            // change state
          }
        }

    }
