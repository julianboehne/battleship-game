package de.htwg.se.battleship.model.fileIOImpl

import de.htwg.se.battleship.controller.state.*
import de.htwg.se.battleship.model.*

import java.io.*
import play.api.libs.json.*
import scala.io.Source

class FIleIOJson extends FileIOInterface {

  override def save(state1: Player1State, state2: Player2State): Unit = {

    /*val pw = new PrintWriter(new File("gameState.json"))
    pw.write(Json.prettyPrint(gameStateToJson(state1, state2)))*/

  }

  def gameStateToJson(state1: Player1State, state2: Player2State) = {
    /*Json.obj(
      "state1" -> Json.obj(
      )
    )*/

  }

  override def load(): PlayerState = ???

}
