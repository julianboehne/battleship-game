package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.GameState.*

import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import scala.swing.*

class GUI(controller: ControllerInterface) extends Frame with Observer:

  val startPanel = StartPanel(controller, this)
  val shipPanel = ShipPanel(controller, this)
  val shotPanel = ShotPanel(controller, this)

  def changeToShipPanel1(): Unit =
    controller.state = controller.player1
    frame.contents = shipPanel.contentPanel

  def changeToShipPanel2(): Unit =
    controller.state = controller.player2
    frame.contents = shipPanel.contentPanel

  def changeToShotPanel(): Unit =
    frame.contents = shotPanel.contentPanel

  val headline = new Label {
    text = "Battleship Game"
    foreground = new Color(0, 0, 0)
    font = new Font("Sans Serif", 0, 24)
  }

  override def update: Unit = {
    if (!controller.player1.grid.getShips().shipCountValid() && !controller.player2.grid.getShips().shipCountValid()) {
      changeToShotPanel()
    } else if (!controller.player1.grid.getShips().shipCountValid()) {
      changeToShipPanel2()
    }
  }



  val frame = new Frame {
    title = "Battleship Game"
    preferredSize = new Dimension(1250, 750)
    resizable = false

    menuBar = new MenuBar {
      contents += new Menu("Options") {
        contents += new MenuItem(Action("New Game") {
          //new Game
        })
        contents += new MenuItem(Action("Exit") {
          System.exit(0)
        })
      }

      contents += new Menu("Edit") {
        contents += new MenuItem(Action("Undo Ship") {
          if (!controller.state.grid.getShips().shipCountValid() || controller.state.grid.getShips().getSize == 0) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else controller.undo()

        })
        contents += new MenuItem(Action("Redo Ship") {
          if (!controller.state.grid.getShips().shipCountValid()) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else controller.redo()


        })
        contents += new MenuItem(Action("Auto Implement Ships") {

          if (controller.state.grid.getShips().getSize != 0 || !controller.state.grid.getShips().shipCountValid()) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else {
            controller.autoShips()
            if (controller.state == controller.player1) changeToShipPanel2()
            else changeToShotPanel()
          }

        })

      }
    }


    listenTo(startPanel.startGameButton)
    reactions += {
      case ButtonClicked(b) =>

        // startPanel.player1.text = Player 1 Name
        // startPanel.player2.text = Player 2 Name
        controller.state = controller.player1
        controller.setPlayerName(startPanel.player1.text)
        controller.state = controller.player2
        controller.setPlayerName(startPanel.player2.text)
        //controller.state = controller.player1


        changeToShipPanel1()

    }
    contents = startPanel.contentPanel


    pack()
    centerOnScreen()
    open()


  }



