package persistency.IO

import persistency.*

import java.io.*
import scala.io.Source
import scala.xml.{NodeSeq, PrettyPrinter, Source}

class FileIOXml extends FileIOInterface {

  override def save(gameData: GameData): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStatetoXml(gameData))
    pw.write(xml)
    pw.close()
  }

  private def gameStatetoXml(gameData: GameData) = {
    <state>
      {<general>
      {<currentState>
        {gameData.currentState}
      </currentState>
        <gameState>
          {gameData.gameState}
        </gameState>}
    </general>
      <state1>
        {<name>
        {gameData.name1}
      </name>
        <grid>
          {<size>
          {gameData.gridSize1}
        </size>
          <shots>
            {<X>
            {gameData.shotsX1.toString()}
          </X>
            <Y>
              {gameData.shotsY1.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {gameData.shipsX1.toString()}
          </shipX>
            <shipY>
              {gameData.shipsY1.toString}
            </shipY>}
          </ships>}
        </grid>}
      </state1>
      <state2>
        {<name>
        {gameData.name2}
      </name>
        <grid>
          {<size>
          {gameData.gridSize2}
        </size>
          <shots>
            {<X>
            {gameData.shotsX2.toString()}
          </X>
            <Y>
              {gameData.shotsY2.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {gameData.shipsX2.toString()}
          </shipX>
            <shipY>
              {gameData.shipsY2.toString}
            </shipY>}
          </ships>}
        </grid>}
      </state2>}
    </state>

  }

  //override def load(): Vector[PlayerState] = ???
  override def load(): (GameData) = {
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

    val shipsX1 = (file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipX").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipsY1 = (file \\ "state" \ "state1" \ "grid" \ "ships" \ "shipY").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipsX2 = (file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipX").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector

    val shipsY2 = (file \\ "state" \ "state2" \ "grid" \ "ships" \ "shipY").text.trim match
      case s: String if s == "Vector()" => Vector.empty[Vector[Int]]
      case s: String => s.stripPrefix("Vector(").stripSuffix(")").split("\\), Vector\\(").map(_.stripPrefix("Vector(").stripSuffix(")")).map(v => v.split(",").map(_.trim.toInt).toVector).toVector


    val currentState = (file \\ "state" \ "general" \ "currentState").text.trim.toInt

    val gameState = (file \\ "state" \ "general" \ "gameState").text.trim

    GameData(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)


  }

}
