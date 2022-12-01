package de.htwg.se.battleship.util

trait Command {

  def doStep: Unit

  def undoStep: Unit

  def redoStep: Unit

}
