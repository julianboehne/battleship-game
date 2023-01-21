package de.htwg.se.battleship.model

import org.scalatest.*
import de.htwg.se.battleship.model.gridImpl.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GridSpec extends AnyWordSpec {


  "Grid" should {
    val nextline: String = sys.props("line.separator")

    "have a getGridShots function" in {
      val gridSize = 1
      val grid1: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
      val test = EmptyGrid(1).fullField


      grid1.getGridShots should be("+----+" + nextline + "|    |" + nextline + "+----+" + nextline)


      val grid2: Grid = Grid(3, Shots(Vector[Int](1,2), Vector[Int](1,4)), ShipContainer(Vector[Ship]()))
      grid2.getGridShots should be(
          "+----+----+----+" + nextline +
          "|  0 | B1 | C1 |" + nextline +
          "+----+----+----+" + nextline +
          "| D1 | E1 | F1 |" + nextline +
          "+----+----+----+" + nextline +
          "| G1 | H1 | I1 |" + nextline +
          "+----+----+----+" + nextline

      )
    }
    "have a getHit function" in {
      val grid1: Grid = Grid(3, Shots(Vector[Int](1,2), Vector[Int](1,4)), ShipContainer(Vector[Ship](Ship(Vector(1,1,1),Vector(1,2,3),3))))
      grid1.getHit(0) should be(true)

    }
    "have a getGridShips function" in {
      val gridSize = 10
      val grid1: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
      val test = EmptyGrid(10).fullField
      grid1.getGridShips should be(test)

      val grid2: Grid = Grid(1, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship](Ship(Vector(1,1,1),Vector(1,2,3),3))))

      grid2.getGridShips should be(
          "+----+" + nextline +
          "|  # |" + nextline +
          "+----+" + nextline
      )
    }
    "have a getShots, getSize and get Ships function" in {
      val grid1: Grid = Grid(3, Shots(Vector[Int](1,2), Vector[Int](1,4)), ShipContainer(Vector[Ship](Ship(Vector(1,1,1),Vector(1,2,3),3))))


      grid1.getShots() should be(Shots(Vector(1,2), Vector(1,4)))
      grid1.getShips() should be(grid1.getShips())
      grid1.getSize() should be(3)
    }
    "have a getX function" in {
      val grid1: Grid = Grid(10, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
      grid1.getX("a") should be(1)


    }
    "have a getNumberSunk function" in {
      val shots = Shots(Vector(1,1),Vector(1,2))
      val ships = ShipContainer(Vector[Ship](Ship(Vector(1,1),Vector(1,2),2),Ship(Vector(2,2),Vector(4,5),2)))
      val grid1 = Grid(10,shots, ships)
      grid1.isSunk(0) should be(true)
      grid1.getNumberSunk should be(1)
    }
  }
  
  }
