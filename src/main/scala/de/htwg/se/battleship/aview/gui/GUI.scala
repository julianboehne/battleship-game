package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
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
    font = new Font("Serif", 0, 24)
  }

  override def update: Unit = {
    //println(controller.toString)
    if (!controller.player1.grid.ships.shipCountValid() && !controller.player2.grid.ships.shipCountValid()) {
      changeToShotPanel()
    } else if (!controller.player1.grid.ships.shipCountValid()) {
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
          if (!controller.state.grid.ships.shipCountValid() || controller.state.grid.ships.getSize == 0) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else controller.undo()

        })
        contents += new MenuItem(Action("Redo Ship") {
          if (!controller.state.grid.ships.shipCountValid()) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else controller.redo()


        })
        contents += new MenuItem(Action("Auto Implement Ships") {

          if (controller.state.grid.ships.getSize != 0 || !controller.state.grid.ships.shipCountValid()) {
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
        if (startPanel.player1.text.trim.isEmpty || startPanel.player2.text.trim.isEmpty) {
          Dialog.showMessage(contents.head, "Text field must not be empty!")
        } else {
          // startPanel.player1.text = Player 1 Name
          // startPanel.player2.text = Player 2 Name
          changeToShipPanel1()
        }
    }
    contents = startPanel.contentPanel


    pack()
    centerOnScreen()
    open()


  }



