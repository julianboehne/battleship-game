package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.util.Observer
import scala.swing.event.ButtonClicked


import javax.swing.border.EmptyBorder
import scala.swing.*

class GUI(controller: Controller) extends Frame:


  new Frame {
    title = "Battleship Game"
    preferredSize = new Dimension(900, 900)
    resizable = true

    menuBar = new MenuBar {

      contents += new Menu("New") {
        contents += new MenuItem(Action("New") {
          //ddd
        })
      }

      contents += new Menu("Edit") {
        contents += new MenuItem(Action("Undo") {
          controller.undo()

        })
        contents += new MenuItem(Action("Redo") {
          controller.redo()

        })

      }
    }
    contents = contentPanel

    def contentPanel = new BorderPanel {
      add(new Label("Battleship Game"), BorderPanel.Position.North) // Label-Bar
      add(new CellPanel(), BorderPanel.Position.Center)             // Game Field
    }

    def update: Unit =
      contents = contentPanel

    class CellPanel() extends GridPanel(10, 10):
      border = EmptyBorder(20, 20, 20, 20)
      printField

      def printField =
        controller.state.grid.board.map(x => contents += new CellButton(x))




    class CellButton(pos: String) extends Button(pos):
      val invert = new TUI(controller)
      listenTo(mouse.clicks)
      listenTo(keys)

      reactions += {

        case ButtonClicked(button) =>
          //tui.addShotInput(pos)
          button.text = "X"
          val x = invert.getX(pos)
          val y = invert.getY(pos)
          controller.addShot(x, y)
          println("Shots X: " + controller.state.grid.shots.X)
          println("Shots Y: " + controller.state.grid.shots.Y)
      }


    pack()
    centerOnScreen()
    open()

  }



