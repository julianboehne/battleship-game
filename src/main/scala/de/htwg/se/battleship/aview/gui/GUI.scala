package de.htwg.se.battleship.aview.gui

import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.aview.*
import de.htwg.se.battleship.util.Observer

import javax.management.Notification
import scala.swing.event.ButtonClicked
import javax.swing.border.EmptyBorder
import scala.swing.*

class GUI(controller: Controller) extends Frame with Observer:

  override def update: Unit = println(controller.toString)



  new Frame {
    title = "Battleship Game"
    preferredSize = new Dimension(1250, 750)
    resizable = false
    val la = new Label("Ich bin ein Label")

    menuBar = new MenuBar {
      contents += la
      contents += new Menu("New") {
        contents += new MenuItem(Action("New") {
          //ddd
          changeText
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
      add(new CellPanel(), BorderPanel.Position.West)             // Game Field
      add(new Label("test") , BorderPanel.Position.South)
      add(new CellPanel(), BorderPanel.Position.East)
    }

    def changeText = {
      val r = Dialog.showInput(contents.head, "New label text", initial = la.text)
      r match {
        case Some(s) => la.text = s
        case None =>
      }
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
          button.text = "0"
          val x = invert.getX(pos)
          val y = invert.getY(pos)
          if(controller.alreadyFired(x,y)) {
            println("You already fired there")

          } else {
            controller.addShot(x, y)
            if (controller.state.grid.ships.isHit(x, y)) button.text = "X"

            // change state
          }

      }




    pack()
    centerOnScreen()
    open()

  }



