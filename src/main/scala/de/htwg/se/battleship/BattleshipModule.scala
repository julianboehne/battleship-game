package de.htwg.se.battleship

import com.google.inject.AbstractModule
import de.htwg.se.battleship.controller.*
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.fileIOImpl.*
import de.htwg.se.battleship.model.gridImpl.{Grid, Ship, ShipContainer, Shots}

class BattleshipModule extends AbstractModule {
  override def configure(): Unit = {
    val gridSize = 10
    val grid: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

    bind(classOf[ControllerInterface]).toInstance(new controllerImpl.Controller(grid))

    bind(classOf[FileIOInterface]).toInstance(new FileIOJson)
    //bind(classOf[FileIOInterface]).toInstance(new FileIOXml)

  }
}
