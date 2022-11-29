

def addShot(x: Int, y: Int, vectorX: Vector[Int], vectorY: Vector[Int]): (Vector[Int], Vector[Int]) = {
  assert(vectorX.size == vectorY.size)
  val vectorXnew = Vector(x)
  val vectorYnew = Vector(y)
  (vectorX ++ vectorXnew, vectorY ++ vectorYnew)

}
addShot(2,5,Vector(3),Vector(7))