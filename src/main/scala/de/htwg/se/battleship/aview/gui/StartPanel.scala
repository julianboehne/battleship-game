package de.htwg.se.battleship.aview.gui

import scala.swing.{BorderPanel, Label}
import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer
import scala.swing.event.*


import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import scala.swing.*
import java.awt.FlowLayout
import javax.swing.JFrame
import javax.swing.JTextField

case class StartPanel(controller: ControllerInterface, gui: GUI) {


  def contentPanel = new BorderPanel {
    add(new Label("Welcome to Battleship Game"), BorderPanel.Position.North)
    add(new startGame, BorderPanel.Position.Center)

    //border = EmptyBorder(350, 400, 350, 400)
    // Label-Bar#
    //
    //add(textField, BorderPanel.Position.South)
    //add(submitButton, BorderPanel.Position.South)


  }

  val player1 = new TextField {
    text = "Player1"
    columns = 19
  }
  val player2 = new TextField {
    text = "Player2"
    columns = 10
  }
  val startGameButton = new Button("Start Game")

  class startGame extends GridPanel(10, 10):
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

