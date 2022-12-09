package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import scala.swing.*

class GUI(controller: Controller) extends Frame with Observer:

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
      contents += new Menu("New") {
        contents += new MenuItem(Action("New") {
          //new Game
        })
      }

      contents += new Menu("Edit") {
        contents += new MenuItem(Action("Undo") {
          controller.undo()

        })
        contents += new MenuItem(Action("Redo") {
          controller.redo()

        })
        contents += new MenuItem(Action("Auto Implement Ships") {
          controller.autoShips()
          if (controller.state == controller.player1) changeToShipPanel2()
          else changeToShotPanel()

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



