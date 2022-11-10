package de.htwg.se.battleship.controller

import de.htwg.se.battleship.util.Observable
import de.htwg.se.battleship.model.*


class Controller(var field: FieldView) extends Observable {
  val game: FieldView = FieldView()


  def gameSetup(): Int = {
    game.startSetup()
    notifyObservers()
    0

  }

  def addShot(x: Int, y: Int): Int = {
    game.setShot(x, y)
    notifyObservers()
    0
  }


}
