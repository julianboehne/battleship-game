package de.htwg.se.battleship.model.fileIOImpl

import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}
import de.htwg.se.battleship.controller.GameState
import de.htwg.se.battleship.controller.GameState.*
import de.htwg.se.battleship.controller.state.*

import java.io.*
import scala.io.Source
import scala.xml.{NodeSeq, PrettyPrinter, Source}

class FileIOXml extends FileIOInterface {

  override def save(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStatetoXml(state1, state2, currentState, gameState))
    pw.write(xml)
    pw.close()
  }

  private def gameStatetoXml(state1: PlayerState, state2: PlayerState, currentState: Int, gameState: GameState) = {
    <state>
      {<general>
      {<currentState>
        {currentState}
      </currentState>
        <gameState>
          {gameState.toString}
        </gameState>}
    </general>
      <state1>
        {<name>
        {state1.getPlayerName}
      </name>
        <grid>
          {<size>
          {state1.grid.size}
        </size>
          <shots>
            {<X>
            {state1.grid.shots.X.toString()}
          </X>
            <Y>
              {state1.grid.shots.Y.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {(0 until state1.grid.ships.getSize).map(state1.grid.ships.shipsVector(_).x).toString}
          </shipX>
            <shipY>
              {(0 until state1.grid.ships.getSize).map(state1.grid.ships.shipsVector(_).y).toString}
            </shipY>}
          </ships>}
        </grid>}
      </state1>
      <state2>
        {<name>
        {state2.getPlayerName}
      </name>
        <grid>
          {<size>
          {state2.grid.size}
        </size>
          <shots>
            {<X>
            {state2.grid.shots.X.toString()}
          </X>
            <Y>
              {state2.grid.shots.Y.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {(0 until state2.grid.ships.getSize).map(state2.grid.ships.shipsVector(_).x).toString}
          </shipX>
            <shipY>
              {(0 until state2.grid.ships.getSize).map(state2.grid.ships.shipsVector(_).y).toString}
            </shipY>}
          </ships>}
        </grid>}
      </state2>}
    </state>

  }

  //override def load(): Vector[PlayerState] = ???
  override def load(): (PlayerState, PlayerState, Int, GameState) = {
    val file = scala.xml.XML.loadFile("gameState.xml")

    val name1 = (file \\ "state" \ "state1" \ "name").text.trim
    val name2 = (file \\ "state" \ "state2" \ "name").text.trim

    val gridSize1 = (file \\ "state" \ "state1" \ "grid" \ "size").text.trim.toInt
    val gridSize2 = (file \\ "state" \ "state2" \ "grid" \ "size").text.trim.toInt

    val shotsX1: Vector[Int] = (file \\ "state" \ "state1" \ "grid" \ "shots" \ "X").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Int]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector

    val shotsY1: Vector[Int] = (file \\ "state" \ "state1" \ "grid" \ "shots" \ "Y").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Int]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector

    val shotsX2: Vector[Int] = (file \\ "state" \ "state2" \ "grid" \ "shots" \ "X").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Int]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector

    val shotsY2: Vector[Int] = (file \\ "state" \ "state2" \ "grid" \ "shots" \ "Y").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Int]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector


    val shots1 = Shots(shotsX1, shotsY1)
    val shots2 = Shots(shotsX2, shotsY2)

    val shipX1 = (file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipX").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipY1 = (file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipY").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipX2 = (file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipX").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipY2 = (file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipY").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector



    val shipContainer1 = ShipContainer(shipX1.zip(shipY1).map { case (x, y) => Ship(x, y, x.size) })
    val shipContainer2 = ShipContainer(shipX2.zip(shipY2).map { case (x, y) => Ship(x, y, x.size) })

    val grid1 = Grid(gridSize1, shots1, shipContainer1)
    val grid2 = Grid(gridSize2, shots2, shipContainer2)

    val state1 = new Player1State(grid1, name1)
    val state2 = new Player1State(grid2, name2)

    val currentState = (file \\ "state" \ "general" \ "currentState").text.trim.toInt

    val gameStateStr = (file \\ "state" \ "general" \ "gameState").text.trim

    val gameState: GameState = GameState.determineGameState(gameStateStr)



    (state1, state2, currentState, gameState)


  }

}
