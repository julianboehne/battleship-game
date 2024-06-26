package gui

import core.controller.*
import core.controller.ControllerInterface
import core.util.GameState.*
import core.util.Observer
import core.util.state.{Player1State, Player2State, PlayerState}
import core.util.state.*

import javax.management.Notification
import javax.swing.border.EmptyBorder
import javax.swing.text.AbstractDocument.Content
import scala.swing.*
import scala.swing.event.ButtonClicked

case class ShotPanel(controller: ControllerInterface, gui: GUI) extends Observer:


  override def update: Unit = gui.update

  def contentPanel: BorderPanel = new BorderPanel {
    add(headPanel, BorderPanel.Position.North)
    add(BoardPanel, BorderPanel.Position.Center)
  }

  private def headPanel = new BorderPanel {
    add(gui.headline, BorderPanel.Position.North) // Headline
    add(player1Text, BorderPanel.Position.West) // Player1 Field
    add(player2Text, BorderPanel.Position.East) // Player2 Field
    player1Text.text = "    " + controller.player1.playerName.get + ": "
      + (controller.player1.grid.ships.getSize - controller.player1.grid.getNumberSunk)
      + "\uD83D\uDEA2 " + controller.player1.grid.getNumberSunk + "\uD83D\uDCA5"
    player2Text.text = controller.player2.playerName.get + ": "
      + (controller.player2.grid.ships.getSize - controller.player2.grid.getNumberSunk)
      + "\uD83D\uDEA2 " + controller.player2.grid.getNumberSunk + "\uD83D\uDCA5" + "    "

  }

  private val player1Text: Label = new Label {
    //text = controller.player1.getPlayerName
    font = new Font("Sans Serif", 0, 20)
    foreground = new Color(5, 88, 242)
  }

  private val player2Text: Label = new Label {
    //text = controller.player2.getPlayerName
    font = new Font("Sans Serif", 0, 20)
    foreground = new Color(17, 171, 3)
  }


  private def BoardPanel = new BorderPanel {

    add(new CellPanel1(), BorderPanel.Position.West) // Player1 Field
    add(new CellPanel2(), BorderPanel.Position.East) // Player2 Field
    controller.changeState()
    add(currPlayer, BorderPanel.Position.South) // Panel Label
    currPlayer.text = controller.state.playerName.get
    controller.changeState()
  }

  private val currPlayer: Label = new Label {
    //text = controller.state.getPlayerName
    font = new Font("Sans Serif", 0, 22)

  }

  //Player1 Grid
  private class CellPanel1 extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField1

    private def printField1 =
      controller.player1.getBoard.map(x =>
        if (x.equals("X")) contents += new CellButton("❌", controller.player1)
        else if (x.equals("0")) contents += new CellButton("⭕", controller.player1)
        else contents += new CellButton(x, controller.player1)

      )

  //Player2 Grid
  private class CellPanel2 extends GridPanel(10, 10):
    border = EmptyBorder(20, 20, 20, 20)
    printField2

    private def printField2 =
      controller.player2.getBoard.map(x =>
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
            println(controller.state.playerName.get + "'s turn'")
            Dialog.showMessage(message = new Label(controller.state.playerName.get + "'s turn'").peer)
            controller.changeState()
          } else {
            controller.addShot(x, y)
            if (controller.isLost) {
              controller.gameState = END
            }
            (controller.state.grid.shots.getLatestX, controller.state.grid.shots.getLatestY) match {
              case (Some(x), Some(y)) =>
                if (!controller.state.grid.ships.isHit(x, y)) {
                  controller.changeState()
                }
              case _ =>
            }
          }

        }
        update

    }
