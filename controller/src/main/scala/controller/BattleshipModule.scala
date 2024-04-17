package controller

import com.google.inject.AbstractModule
import model.gridImpl.*
import persistency.*
import persistency.IO.*
import controller.controllerImpl.*


class BattleshipModule extends AbstractModule {
  override def configure(): Unit = {
    val gridSize = 10
    val grid: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

    bind(classOf[ControllerInterface]).toInstance(new Controller(grid))

    //bind(classOf[model.FileIOInterface]).toInstance(new FileIOJson)
    bind(classOf[FileIOInterface]).toInstance(new FileIOXml)

  }
}
