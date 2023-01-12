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

case class EndPanel(controller: ControllerInterface, gui: GUI) {

  val headline = new Label {
    text = "Game Finished"
    foreground = new Color(0, 0, 0)
    font = new Font("Sans Serif", 0, 36)
  }


  def contentPanel = new BorderPanel {
    this.background = new Color(170, 241, 242)
    add(headline, BorderPanel.Position.North)
    add(label, BorderPanel.Position.Center)
    label.text = controller.state.getPlayerName + " has won the game!"
    add(new ExitGame, BorderPanel.Position.South)

  }

  val label = new Label {
    controller.changeState()
    font = new Font("Sans Serif", 0, 30)
    foreground = new Color(94, 199, 2)


  }


  class ExitGame extends GridPanel(10, 10):
    border = EmptyBorder(100, 200, 100, 200)

    contents += new ExitButton

  class ExitButton extends Button("Exit Game"):

    listenTo(mouse.clicks)
    listenTo(keys)
    reactions += {
      case ButtonClicked(button) => System.exit(0)


    }



}

