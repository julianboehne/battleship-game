package core.controller

import com.google.inject.AbstractModule
import core.model.gridImpl.*
import persistency.*
import persistency.IO.*
import core.controller.controllerImpl.*
import core.controller.controllerImpl.Controller
import core.model.gridImpl.{Grid, Ship, ShipContainer, Shots}


class BattleshipModule extends AbstractModule {
  override def configure(): Unit = {
    val gridSize = 10
    val grid: Grid = Grid(gridSize, Shots(Vector[Int](), Vector[Int]()), ShipContainer(Vector[Ship]()))

    bind(classOf[ControllerInterface]).toInstance(new Controller(grid))

//    bind(classOf[FileIOInterface]).toInstance(new FileIOJson)
    bind(classOf[FileIOInterface]).toInstance(new FileIOXml)

  }
}
