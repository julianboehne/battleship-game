package de.htwg.se.battleship

import com.google.inject.AbstractModule
import controller.{ControllerInterface, *}
import de.htwg.se.battleship.model.*
import de.htwg.se.battleship.model.fileIOImpl.*

class BattleshipModule extends AbstractModule {
  override def configure(): Unit = {
    val gridSize = 10
    val grid: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

    bind(classOf[ControllerInterface]).toInstance(new Controller(grid))

    //bind(classOf[model.FileIOInterface]).toInstance(new FileIOJson)
    bind(classOf[FileIOInterface]).toInstance(new FileIOXml)

  }
}
