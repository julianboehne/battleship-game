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

case class ShotPanel(controller: ControllerInterface, gui: GUI) extends Observer:


  override def update: Unit = println(controller.GridShipToString)

  def contentPanel = new BorderPanel {
    //controller.state = controller.player1
    add(gui.headline, BorderPanel.Position.North)     // Label-Bar
    add(new CellPanel1(), BorderPanel.Position.West)  // Player1 Field
    add(new CellPanel2(), BorderPanel.Position.East)  // Player2 Field
    add(new Label("Shot Panel"), BorderPanel.Position.South)  // Panel Label
  }

  //Player1 Grid
  class CellPanel1() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField1

    def printField1 =
      controller.player1.getBoard().map(x => contents += new CellButton(x, controller.player1))

  //Player2 Grid
  class CellPanel2() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField2

    def printField2 =
      controller.player2.getBoard().map(x => contents += new CellButton(x, controller.player2))


  class CellButton(pos: String, player: PlayerState) extends Button(pos):
    player match
      case controller.player1 => this.background = new Color(151, 164, 222)
      case controller.player2 => this.background = new Color(151, 222, 178)
    listenTo(mouse.clicks)
    //listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>
        if (button.text.equals("X")) {
          println("You already fired there")
          Dialog.showMessage(message = new Label("You already fired there").peer)
        } else {
          button.text = "0"

          val x = controller.getX(pos)
          val y = controller.getY(pos)
          if (controller.alreadyFired(x, y)) {
            println("You already fired there")
            Dialog.showMessage(message = new Label("You already fired there").peer)
          } else {
            controller.state = player
            controller.addShot(x, y)
            if (player.grid.ships.isHit(x, y)) button.text = "X"

          }
        }

    }
