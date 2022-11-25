package de.htwg.se.battleship.controller

import de.htwg.se.battleship.util.Observable
import de.htwg.se.battleship.model.*


class Controller(val fld1: Field, val fld2: Field) extends Observable {
  val field1: Field = fld1
  val field2: Field = fld2
  var state: Int = 0

  def gameSetup(): String = {
    val str0 = s"${Console.MAGENTA}${field1.field.nextline}"
    val str1 = str0 + field1.emptyField() + field2.emptyField()
    s"$str1${Console.RESET}"

  }


  def addShot(x: Int, y: Int, fld: Field): Int = {
    //isHIT
    val success = fld.shots.addShot(x, y, true)
    if (success == 1) return success
    notifyObservers
    success
  }

  def getField: Field = {
    if (state == 0) return field1
    field2
  }

  def setField(): Int = {
    if (state == 0) state = 1
    else state = 0
    state
  }

  override def toString: String = getField.setShot()


}
