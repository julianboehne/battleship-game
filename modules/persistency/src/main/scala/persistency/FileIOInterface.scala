package persistency

trait FileIOInterface {
  def save(currentState: Int, gameState: String, gridSize1: Int, gridSize2: Int, name1: String, name2: String, shotsX1: Vector[Int], shotsY1: Vector[Int], shotsX2: Vector[Int], shotsY2: Vector[Int], shipsX1: Vector[Vector[Int]], shipsY1: Vector[Vector[Int]], shipsX2: Vector[Vector[Int]], shipsY2: Vector[Vector[Int]]): Unit

  //currentState, gameState, gridSize1, gridSize2, name1, name2, shotsX1, shotsY1, shotsX2, shotsY2, shipsX1, shipsY1, shipsX2, shipsY2
  def load(): (Int, String, Int, Int, String, String, Vector[Int], Vector[Int], Vector[Int], Vector[Int], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]], Vector[Vector[Int]])

}
