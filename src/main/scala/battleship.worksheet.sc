val nextline: String = sys.props("line.separator")

def horizontal(width: Int, count: Int):
String = ("+" + "-" * width) * count + "+" + nextline

def vertical(width: Int, count: Int):
String = ("|" + " " * width) * count + "|" + nextline

def field(width: Int, count: Int):
String = ( horizontal(width, count) + vertical(width, count) ) * count + horizontal(width, count)

println("Battleship Game")

println("Enemy")
println(field(width = 4, count = 10))
print(nextline)
println("You")
println(field(width = 4, count = 10))