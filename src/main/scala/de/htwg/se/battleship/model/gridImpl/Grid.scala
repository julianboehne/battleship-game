package de.htwg.se.battleship.model.gridImpl

import de.htwg.se.battleship.model.*

import scala.util.control.NonLocalReturns.{returning, throwReturn}



case class Grid(size: Int, shots: Shots, ships: ShipContainer) extends GridInterface {
  //Grid for Shots

  override def getGridShots: String = {
    if (shots.X.isEmpty && shots.Y.isEmpty) return EmptyGrid(size).toString
    GridForShots(size, getBoard()).toString
  }

  override def getHit(i: Int): Boolean =
    ships.isHit(shots.getX(i), shots.getY(i))


  override def getGridShips: String = {
    if (ships.shipsVector.isEmpty) return EmptyGrid(size).toString
    GridForShips(size, ships).toString
  }

  override def getShots(): Shots = shots

  override def getSize(): Int = size

  override def getShips(): ShipContainer = ships

  val board: Vector[String] = Vector("A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "I2", "J2", "A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "I3", "J3", "A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "I4", "J4", "A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "I5", "J5", "A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "I6", "J6", "A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "I7", "J7", "A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "I8", "J8", "A9", "B9", "C9", "D9", "E9", "F9", "G9", "H9", "I9", "J9", "A10", "B10", "C10", "D10", "E10", "F10", "G10", "H10", "I10", "J10")

  override def getBoard(): Vector[String] =
    board.indices.map(x => {
      if (shots.wasShot(getX(board(x)), getY(board(x)))) {
        if (ships.isHit(getX(board(x)), getY(board(x)))) {
          "X"
        }
        else "0"
      } else board(x)

    }).toVector

  def getX(input: String): Int = {

      val char = "([a-j]|[A-J])".r.findAllIn(input).mkString

      if (char.matches("[a-j]")) {
        return char.charAt(0) - 'a' + 1
      }
      char.charAt(0) - 'A' + 1

  }

  def getY(input: String): Int = "(10)|([1-9])".r.findAllIn(input).mkString.toInt

  override def getNumberSunk: Int = {

    val sunk: Vector[Int] = (0 until getShips().getSize).map(index =>
      if(isSunk(index)) 1
      else 0
    ).toVector

    sunk.sum

  }

  override def isSunk(index: Int): Boolean = returning {
    (0 until getShips().shipsVector(index).size).foreach(i =>
      if (!getShots().wasShot(getShips().shipsVector(index).getX(i), getShips().shipsVector(index).getY(i))) throwReturn(false)
    )
    true

  }




  
}






