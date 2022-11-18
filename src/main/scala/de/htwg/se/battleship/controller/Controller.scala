package de.htwg.se.battleship.controller

import de.htwg.se.battleship.util.Observable
import de.htwg.se.battleship.model.*


class Controller(var fld: FieldView) extends Observable {
  val field: FieldView = fld
  val shots: Shot = new Shot


  def gameSetup(): String = {
    field.startSetup()
  }

  def addShot(x: Int, y: Int): Int = {
    shots.addShot(x, y)
    notifyObservers
    0
  }

  override def toString: String = field.setShot(shots.getX(shots.size), shots.getY(shots.size))


}
