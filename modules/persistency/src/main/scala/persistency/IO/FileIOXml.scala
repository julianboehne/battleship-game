package persistency.IO

import persistency.FileIOInterface

import java.io.*
import scala.io.Source
import scala.xml.{NodeSeq, PrettyPrinter, Source}

class FileIOXml extends FileIOInterface {

  override def save(currentState: Int, gameState: String, gridSize1: Int, gridSize2: Int, name1: String, name2: String, shotsX1: Vector[Int], shotsY1: Vector[Int], shotsX2: Vector[Int], shotsY2: Vector[Int], shipsX1: Vector[Vector[Int]], shipsY1: Vector[Vector[Int]], shipsX2: Vector[Vector[Int]], shipsY2: Vector[Vector[Int]]): Unit = {
    val pw = new PrintWriter(new File("gameState.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStatetoXml(currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2))
    pw.write(xml)
    pw.close()
  }

  private def gameStatetoXml(currentState: Int, gameState: String, gridSize1: Int, gridSize2: Int, name1: String, name2: String, shotsX1: Vector[Int], shotsY1: Vector[Int], shotsX2: Vector[Int], shotsY2: Vector[Int], shipsX1: Vector[Vector[Int]], shipsY1: Vector[Vector[Int]], shipsX2: Vector[Vector[Int]], shipsY2: Vector[Vector[Int]]) = {
    <state>
      {<general>
      {<currentState>
        {currentState}
      </currentState>
        <gameState>
          {gameState}
        </gameState>}
    </general>
      <state1>
        {<name>
        {name1}
      </name>
        <grid>
          {<size>
          {gridSize1}
        </size>
          <shots>
            {<X>
            {shotsX1.toString()}
          </X>
            <Y>
              {shotsY1.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {shipsX1.toString()}
          </shipX>
            <shipY>
              {shipsY1.toString}
            </shipY>}
          </ships>}
        </grid>}
      </state1>
      <state2>
        {<name>
        {name2}
      </name>
        <grid>
          {<size>
          {gridSize2}
        </size>
          <shots>
            {<X>
            {shotsX2.toString()}
          </X>
            <Y>
              {shotsY2.toString}
            </Y>}
          </shots>
          <ships>
            {<shipX>
            {shipsX2.toString()}
          </shipX>
            <shipY>
              {shipsY2.toString}
            </shipY>}
          </ships>}
        </grid>}
      </state2>}
    </state>

  }

  //override def load(): Vector[PlayerState] = ???
  override def load(): (Int, String, Int, Int, String, String, Vector[Int], Vector[Int], Vector[Int], Vector[Int], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]]) = {
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

    (currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2)


  }

}
