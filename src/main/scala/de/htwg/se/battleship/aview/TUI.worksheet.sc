def isValid(input: String): Boolean = {
  input.matches("^(([a-j]|[A-J])((10)|([1-9])))$")

}

def inputLine(input: String): Int = {

  if (!isValid(input)) {
    return 1
  }

  val char = "([a-j]|[A-J])".r.findAllIn(input).mkString
  val num =  "(10)|([1-9])".r.findAllIn(input).mkString.toInt

  printf("X-Wert: %s\nY-Wert: %d\n",char, num)
  return 0
}

inputLine("H5")
//isValid("H5")