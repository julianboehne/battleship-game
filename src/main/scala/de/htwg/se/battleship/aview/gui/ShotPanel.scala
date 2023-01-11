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

case class ShotPanel(controller: ControllerInterface, gui: GUI) extends Observer:


  override def update: Unit = gui.update

  def contentPanel = new BorderPanel {
    add(p, BorderPanel.Position.North)
    add(test, BorderPanel.Position.Center)
  }

  def p = new BorderPanel {
    add(gui.headline, BorderPanel.Position.North) // Label-Bar
    add(new Label(controller.player1.getPlayerName), BorderPanel.Position.West) // Player1 Field
    add(new Label(controller.player2.getPlayerName), BorderPanel.Position.East) // Player2 Field
  }

  def test = new BorderPanel {

    add(new CellPanel1(), BorderPanel.Position.West) // Player1 Field
    add(new CellPanel2(), BorderPanel.Position.East) // Player2 Field
    controller.changeState()
    add(new Label(controller.state.getPlayerName), BorderPanel.Position.South) // Panel Label
    controller.changeState()
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
      case _: Player1State => this.background = new Color(151, 164, 222)
      case _: Player2State => this.background = new Color(151, 222, 178)
    listenTo(mouse.clicks)
    //listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>

          if (button.text == "0" || button.text == "X") {
            println("You already fired there")
            Dialog.showMessage(message = new Label("You already fired there").peer)
          } else {
            val x = controller.getX(pos)
            val y = controller.getY(pos)
            if (controller.state != player) {
              println(controller.state.getPlayerName + "'s turn'")
              Dialog.showMessage(message = new Label(controller.state.getPlayerName + "'s turn'").peer)
            } else {
              controller.addShot(x, y)
              controller.changeState()
            }

          }
          update

    }
