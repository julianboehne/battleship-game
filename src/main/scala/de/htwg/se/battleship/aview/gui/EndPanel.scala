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

  val headline: Label = new Label {
    text = "Game Finished"
    foreground = new Color(0, 0, 0)
    font = new Font("Sans Serif", 0, 36)
  }


  def contentPanel: BorderPanel = new BorderPanel {
    background = new Color(195, 247, 230)
    add(headline, BorderPanel.Position.North)
    add(label, BorderPanel.Position.Center)
    label.text = controller.state.getPlayerName + " has won the game! \uD83C\uDF89"
    add(new ExitGame, BorderPanel.Position.South)

  }

  private val label = new Label {
    controller.changeState()
    font = new Font("Sans Serif", 0, 26)
    foreground = new Color(94, 199, 2)


  }


  private class ExitGame extends GridPanel(10, 10):
    border = EmptyBorder(50, 150, 50, 150)
    background = new Color(195, 247, 230)
    contents += new ExitButton

  private class ExitButton extends Button("Exit Game⛔"):
    font = new Font("Sans Serif", 0, 22)
    foreground = new Color(194, 0, 0)
    background = new Color(255, 158, 158)

    listenTo(mouse.clicks)
    listenTo(keys)
    reactions += {
      case ButtonClicked(button) => System.exit(0)


    }



}

