package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import java.awt.FlowLayout
import javax.management.Notification
import javax.swing.{JFrame, JTextField}
import javax.swing.border.EmptyBorder
import scala.swing.event.*
import scala.swing.*

case class StartPanel(controller: ControllerInterface, gui: GUI) {

  val headline: Label = new Label {
    text = "Welcome to Battleship Game"
    foreground = new Color(0, 0, 0)
    font = new Font("Sans Serif", 0, 24)
  }


  def contentPanel: BorderPanel = new BorderPanel {
    add(headline, BorderPanel.Position.North)
    add(new startGame, BorderPanel.Position.Center)

  }

  val player1: TextField = new TextField {
    text = ""
    columns = 19
  }
  val player2: TextField = new TextField {
    text = ""
    columns = 10
  }
  val startGameButton = new Button("Start Game")

  private class startGame extends GridPanel(10, 10):
    border = EmptyBorder(100, 300, 20, 300)

    contents += player1
    contents += player2
    contents += startGameButton


  /*  class startGameButton extends Button("Start Game"):
  
      listenTo(mouse.clicks)
      listenTo(keys)
      reactions += {
        case ButtonClicked(button) => gui.changeToShipPanel()
  
  
      }*/


}

