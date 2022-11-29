package de.htwg.se.battleship
package aview

import controller.*
import util.*

class TUI (controller: Controller) extends Observer{
    controller.add(this)
    
}
