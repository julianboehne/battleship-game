def getShip(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
  if (x1 == x2 && y1 != y2) {
    val size = Math.abs(y2 - y1) + 1
    val x: Vector[Int] = ((1 to size).map(x => x1)).toVector
    val y: Vector[Int] = (y1 to y2).toVector
    println(x)



  }
}
getShip(1,2,1,5)
