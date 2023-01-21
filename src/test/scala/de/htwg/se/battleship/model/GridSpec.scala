package de.htwg.se.battleship.model

import org.scalatest.*
import de.htwg.se.battleship.model.gridImpl.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GridSpec extends AnyWordSpec {


  "Grid" should {
    val nextline: String = sys.props("line.separator")

    "have a getGridShots function" in {
      val gridSize = 10
      val grid1: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
      val test = EmptyGrid(10).fullField
      grid1.getGridShots should be(test)


      val grid2: Grid = Grid(3, Shots(Vector[Int](1,2), Vector[Int](1,4)), ShipContainer(Vector[Ship]()))
      println(grid2.getGridShots)
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


    }
    "have a getGridShips function" in {
      val gridSize = 10
      val grid1: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))
      val test = EmptyGrid(10).fullField
      grid1.getGridShips should be(test)

      //val grid2: Grid = Grid(3, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship](Ship)))
    }
  }
  
  }
