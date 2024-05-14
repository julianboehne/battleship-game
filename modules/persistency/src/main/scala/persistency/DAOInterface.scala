package persistency

trait FileIOInterface {
  def save(gameData: GameData): Unit

  def load(): GameData
}

case class GameData(
                     currentState: Int,
                     gameState: String,
                     gridSize1: Int,
                     gridSize2: Int,
                     name1: String,
                     name2: String,
                     shotsX1: Vector[Int],
                     shotsY1: Vector[Int],
                     shotsX2: Vector[Int],
                     shotsY2: Vector[Int],
                     shipsX1: Vector[Vector[Int]],
                     shipsY1: Vector[Vector[Int]],
                     shipsX2: Vector[Vector[Int]],
                     shipsY2: Vector[Vector[Int]]
                   )