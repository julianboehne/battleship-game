object Battleship {
  def main(args: Array[String]): Unit = {
    println("Battleship Game")

    println(Console.RED + "Enemy")
    println(field(width = 4, count = 10))
    print(nextline)
    println(Console.BLUE + "You")
    println(field(width = 4, count = 10))
    print(Console.RESET)
  }

  val nextline: String = sys.props("line.separator")

  def horizontal(width: Int, count: Int):
  String = ("+" + "-" * width) * count + "+" + nextline

  def vertical(width: Int, count: Int):
  String = ("|" + " " * width) * count + "|" + nextline

  def field(width: Int, count: Int):
  String = ( horizontal(width, count) + vertical(width, count) ) * count + horizontal(width, count)

}