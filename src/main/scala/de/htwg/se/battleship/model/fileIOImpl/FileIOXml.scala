package de.htwg.se.battleship.model.fileIOImpl

import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*
import java.io.*
import scala.xml.{NodeSeq,PrettyPrinter}

class FileIOXml extends FileIOInterface {

  override def save(state1: Player1State, state2: Player2State): Unit = {
    /*val pw = new PrintWriter(new File("grid.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(stateToXml(state1,state2))
    pw.write(xml)
    pw.close()

  def stateToXml(state1: Player1State, state2: Player2State) = {
    */

  }

  override def load(): PlayerState = ???

}
