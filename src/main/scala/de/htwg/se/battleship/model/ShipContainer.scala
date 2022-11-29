package de.htwg.se.battleship.model


class ShipContainer {
  def addShip(shipsVector: Vector[Ships], ship: Ships): Vector[Ships] = shipsVector :+ ship

  def getShip(shipsVector: Vector[Ships], index:Int) : Ships = shipsVector(index)

}
