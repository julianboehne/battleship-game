package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.controllerImpl.Controller
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import javax.swing.border.EmptyBorder
import scala.swing.*
import scala.swing.event.ButtonClicked

class GUI(controller: ControllerInterface) extends Frame with Observer:

  private val startPanel = StartPanel(controller, this)
  private val shipPanel = ShipPanel(controller, this)
  private val shotPanel = ShotPanel(controller, this)
  private val endPanel = EndPanel(controller, this)


  private def changeToStartPanel(): Unit =
    frame.contents = startPanel.contentPanel

  private def changeToShipPanel1(): Unit =
    controller.state = controller.player1
    frame.contents = shipPanel.contentPanel

  private def changeToShipPanel2(): Unit =
    controller.state = controller.player2
    frame.contents = shipPanel.contentPanel

  private def changeToShotPanel(): Unit =
    frame.contents = shotPanel.contentPanel

  private def changeToEndPanel(): Unit =
    frame.contents = endPanel.contentPanel


  val headline: Label = new Label {
    text = "Battleship Game"
    foreground = new Color(0, 0, 0)
    font = new Font("Sans Serif", 0, 24)
  }

  override def update: Unit = {
    controller.gameState match
      case PLAYER_CREATE1 => changeToStartPanel()
      case PLAYER_CREATE2 =>
      case SHIP_PLAYER1 => changeToShipPanel1()
      case SHIP_PLAYER2 => changeToShipPanel2()
      case SHOTS => changeToShotPanel()
      case END => changeToEndPanel()

  }


  private val frame = new Frame {
    title = "Battleship Game"
    preferredSize = new Dimension(1250, 750)
    resizable = false

    menuBar = new MenuBar {
      contents += new Menu("Options") {
        contents += new MenuItem(Action("New Game") {
          //new Game
          controller.resetGame()
          println("New Game:")
          Dialog.showMessage(message = new Label("New Game").peer)
          update

        })

        contents += new MenuItem(Action("Save Game") {
          //Save Game
          controller.saveGame()
          println("Game saved")
          Dialog.showMessage(message = new Label("Game saved").peer)
          //update

        })

        contents += new MenuItem(Action("Load Game") {
          //Save Game
          controller.loadGame()
          println("Load saved")
          Dialog.showMessage(message = new Label("Game loaded").peer)
          update

        })

        contents += new MenuItem(Action("Exit") {
          //Exit
          System.exit(0)
        })
      }

      contents += new Menu("Edit") {
        contents += new MenuItem(Action("Undo Ship") {
          if (controller.gameState != SHIP_PLAYER1 && controller.gameState != SHIP_PLAYER2) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else {
            controller.undo()
            update
          }

        })
        contents += new MenuItem(Action("Redo Ship") {
          if (controller.gameState != SHIP_PLAYER1 && controller.gameState != SHIP_PLAYER2) {
            println("invalid command")
            Dialog.showMessage(message = new Label("invalid command").peer)
          } else {
            controller.redo()
            update
          }


        })
        contents += new MenuItem(Action("Auto Implement Ships") {

          controller.gameState match
            case SHIP_PLAYER1 =>
              if (controller.player1.grid.ships.getSize != 0) {
                println("invalid command")
                Dialog.showMessage(message = new Label("invalid command").peer)
              } else {
                controller.state = controller.player1
                controller.autoShips()
                controller.gameState = SHIP_PLAYER2
              }
            case SHIP_PLAYER2 =>
              if (controller.player2.grid.ships.getSize != 0) {
                println("invalid command")
                Dialog.showMessage(message = new Label("invalid command").peer)
              } else {
                controller.state = controller.player2
                controller.autoShips()
                controller.gameState = SHOTS
              }
            case _ =>
              println("invalid command")
              Dialog.showMessage(message = new Label("invalid command").peer)

          update

        })

      }
    }


    listenTo(startPanel.startGameButton)
    reactions += {
      case ButtonClicked(b) =>

        controller.state = controller.player1
        controller.setPlayerName(startPanel.player1.text)
        controller.state = controller.player2
        controller.setPlayerName(startPanel.player2.text)
        controller.gameState = SHIP_PLAYER1
        update


    }

    contents = startPanel.contentPanel


    pack()
    centerOnScreen()
    open()


  }



