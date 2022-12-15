package de.htwg.se.battleship.aview

trait tuiInterface {

  def processInputLine(input: String): Unit

  def addShotInput(input: String): Int

  def checkFired(input: String): Boolean

  def removeShip(): Unit

  def redoShip(): Unit

  def addShipInput(start: String, ende: String): Unit

  def shipStartInput(line1: String): Unit

}
