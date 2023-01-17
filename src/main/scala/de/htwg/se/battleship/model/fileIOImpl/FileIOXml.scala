package de.htwg.se.battleship.model.fileIOImpl

import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

import java.io.*
import scala.io.Source
import scala.xml.{NodeSeq, PrettyPrinter, Source}

class FileIOXml extends FileIOInterface {

  override def save(state1: PlayerState, state2: PlayerState): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStatetoXml(state1, state2))
    pw.write(xml)
    pw.close()
  }

  def gameStatetoXml(state1: PlayerState, state2: PlayerState) = {
    <state>
      {<state1>
      {<name>
        {state1.getPlayerName}
      </name>
        <grid>
          {<size>
          {state1.grid.getSize()}
        </size>
          <shots>
            {<X>
            {state1.grid.getShots().X.toString()}
          </X>
            <Y>
              {state1.grid.getShots().Y.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {(0 until state1.grid.getShips().getSize).map(i => state1.grid.getShips().shipsVector(i).x).toString}
          </shipX>
            <shipY>
              {(0 until state1.grid.getShips().getSize).map(i => state1.grid.getShips().shipsVector(i).y).toString}
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
          {state2.grid.getSize()}
        </size>
          <shots>
            {<X>
            {state2.grid.getShots().X.toString()}
          </X>
            <Y>
              {state2.grid.getShots().Y.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {(0 until state2.grid.getShips().getSize).map(i => state2.grid.getShips().shipsVector(i).x).toString}
          </shipX>
            <shipY>
              {(0 until state2.grid.getShips().getSize).map(i => state2.grid.getShips().shipsVector(i).y).toString}
            </shipY>}
          </ships>}
        </grid>}
      </state2>}
    </state>

  }

  //override def load(): Vector[PlayerState] = ???
  override def load(): Vector[PlayerState] = {
    val file = scala.xml.XML.loadFile("gameState.xml")
    println("name1:" + (file \\ "state" \ "state1" \ "name").text.trim)
    println("name2:" + (file \\ "state" \ "state2" \ "name").text.trim)

    println("size1:" + (file \\ "state" \ "state1" \ "grid" \ "size").text.trim.toInt)
    println("size2:" + (file \\ "state" \ "state2" \ "grid" \ "size").text.trim.toInt)

    println("shotx1:" + (file \\ "state" \ "state1" \ "grid" \ "shots" \ "X").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector)
    println("shoty1:" + (file \\ "state" \ "state1" \ "grid" \ "shots" \ "Y").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector)

    println("shotx2:" + (file \\ "state" \ "state2" \ "grid" \ "shots" \ "X").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector)
    println("shoty2:" + (file \\ "state" \ "state2" \ "grid" \ "shots" \ "Y").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector)

    println("shipx1:" + Vector((file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipX").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*))
    println("shipy1:" + Vector((file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipY").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*))

    println("shipx2:" + Vector((file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipX").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*))
    println("shipy2:" + Vector((file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipY").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*))


    val name1 = (file \\ "state" \ "state1" \ "name").text.trim
    val name2 = (file \\ "state" \ "state2" \ "name").text.trim

    val gridSize1 = (file \\ "state" \ "state1" \ "grid" \ "size").text.trim.toInt
    val gridSize2 = (file \\ "state" \ "state2" \ "grid" \ "size").text.trim.toInt

    val shotsX1 = (file \\ "state" \ "state1" \ "grid" \ "shots" \ "X").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector
    val shotsY1 = (file \\ "state" \ "state1" \ "grid" \ "shots" \ "Y").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector

    val shotsX2 = (file \\ "state" \ "state2" \ "grid" \ "shots" \ "X").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector
    val shotsY2 = (file \\ "state" \ "state2" \ "grid" \ "shots" \ "Y").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector

    val shots1 = new Shots(shotsX1, shotsY1)
    val shots2 = new Shots(shotsX2, shotsY2)

    val shipX1 = Vector((file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipX").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*)
    val shipY1 = Vector((file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipY").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*)

    val shipX2 = Vector((file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipX").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*)
    val shipY2 = Vector((file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipY").text.trim.stripPrefix("Vector(").stripSuffix(")").split(", ").toList.map(x => x.stripPrefix("Vector(").stripSuffix(")").split(", ").map(_.toInt).toVector): _*)


    val vec1: Vector[Ship] = shipX1.zip(shipY1).map{ case(x,y) => Ship(x,y,x.length)}.toVector
    val vec2: Vector[Ship] = shipX2.zip(shipY2).map{ case(x,y) => Ship(x,y,x.length)}.toVector
    println(vec1)
    val shipContainer1 = ShipContainer(vec1)
    val shipContainer2 = ShipContainer(vec2)

    val grid1 = Grid(gridSize1, shots1, shipContainer1)
    val grid2 = Grid(gridSize2, shots2, shipContainer2)

    val state1 = new Player1State(grid1, name1)
    val state2 = new Player1State(grid2, name2)

    val stateVector: Vector[PlayerState] = Vector[PlayerState](state1, state2)
    stateVector

  }

}
