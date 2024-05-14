package core.util

object GameState extends Enumeration {
  type GameState = Value
  val PLAYER_CREATE1, PLAYER_CREATE2, SHIP_PLAYER1, SHIP_PLAYER2, SHOTS, END = Value

  val map: Map[GameState, String] = Map[GameState, String](
    PLAYER_CREATE1 -> "Please enter your name",
    PLAYER_CREATE2 -> "Please enter your name",
    SHIP_PLAYER1 -> "Please enter your ships",
    SHIP_PLAYER2 -> "Please enter your ships",
    SHOTS -> "Please enter your Shot",
    END -> "Thank you for playing Battleship-Game"
  )

  def message(gameState: GameState): String = {
    map(gameState)
  }

  def determineGameState(gameStateStr: String): GameState = gameStateStr match {
    case "PLAYER_CREATE1" => PLAYER_CREATE1
    case "PLAYER_CREATE2" => PLAYER_CREATE2
    case "SHIP_PLAYER1" => SHIP_PLAYER1
    case "SHIP_PLAYER2" => SHIP_PLAYER2
    case "SHOTS" => SHOTS
    case "END" => END
    case _ => throw new IllegalArgumentException(s"Invalid game state: $gameStateStr")
  }

}
