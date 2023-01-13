package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.GameState.*

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
    add(headPanel, BorderPanel.Position.North)
    add(BoardPanel, BorderPanel.Position.Center)
  }

  def headPanel = new BorderPanel {
    add(gui.headline, BorderPanel.Position.North) // Headline
    add(player1Text, BorderPanel.Position.West) // Player1 Field
    add(player2Text, BorderPanel.Position.East) // Player2 Field
    player1Text.text = "    " + controller.player1.getPlayerName + ": "
      + (controller.player1.grid.getShips().getSize-controller.player1.grid.getNumberSunk)
      + "\uD83D\uDEA2 " + controller.player1.grid.getNumberSunk + "\uD83D\uDCA5"
    player2Text.text = controller.player2.getPlayerName + ": "
      + (controller.player2.grid.getShips().getSize - controller.player2.grid.getNumberSunk)
      + "\uD83D\uDEA2 " + controller.player2.grid.getNumberSunk + "\uD83D\uDCA5" + "    "

  }

  val player1Text: Label = new Label {
    //text = controller.player1.getPlayerName
    font = new Font("Sans Serif", 0, 20)
    foreground = new Color(5, 88, 242)
  }

  val player2Text: Label = new Label {
    //text = controller.player2.getPlayerName
    font = new Font("Sans Serif", 0, 20)
    foreground = new Color(17, 171, 3)
  }






  def BoardPanel = new BorderPanel {

    add(new CellPanel1(), BorderPanel.Position.West) // Player1 Field
    add(new CellPanel2(), BorderPanel.Position.East) // Player2 Field
    controller.changeState()
    add(currPlayer, BorderPanel.Position.South) // Panel Label
    currPlayer.text = controller.state.getPlayerName
    controller.changeState()
  }

  val currPlayer: Label = new Label {
    //text = controller.state.getPlayerName
    font = new Font("Sans Serif", 0, 22)

  }

  //Player1 Grid
  class CellPanel1() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField1

    def printField1 =
      controller.player1.getBoard().map(x =>
        if (x.equals("X")) contents += new CellButton("❌", controller.player1)
        else if (x.equals("0")) contents += new CellButton("⭕", controller.player1)
        else contents += new CellButton(x, controller.player1)

      )

  //Player2 Grid
  class CellPanel2() extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField2

    def printField2 =
      controller.player2.getBoard().map(x =>
        if (x.equals("X")) contents += new CellButton("❌", controller.player2)
        else if (x.equals("0")) contents += new CellButton("⭕", controller.player2)
        else contents += new CellButton(x, controller.player2)

      )


  class CellButton(pos: String, player: PlayerState) extends Button(pos):
    player match
      case _: Player1State => this.background = new Color(151, 164, 222)
      case _: Player2State => this.background = new Color(151, 222, 178)
    listenTo(mouse.clicks)
    //listenTo(keys)
    reactions += {

      case ButtonClicked(button) =>

          if (button.text == "⭕" || button.text == "❌") {
            println("You already fired there")
            Dialog.showMessage(message = new Label("You already fired there").peer)
          } else {
            val x = controller.getX(pos)
            val y = controller.getY(pos)
            if (controller.state != player) {
              controller.changeState()
              println(controller.state.getPlayerName + "'s turn'")
              Dialog.showMessage(message = new Label(controller.state.getPlayerName + "'s turn'").peer)
              controller.changeState()
            } else {
              controller.addShot(x, y)
              if (controller.isLost()) {
                controller.gameState = END
              }
              controller.changeState()

            }

          }
          update

    }
