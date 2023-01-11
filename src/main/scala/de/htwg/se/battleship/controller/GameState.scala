package de.htwg.se.battleship.controller

object GameState extends Enumeration {
  type GameState = Value
  val PLAYER_CREATE, SHIP_PLAYER1, SHIP_PLAYER2, SHOTS, END = Value

  val map = Map[GameState, String](
    PLAYER_CREATE -> "Please enter your name",
    SHIP_PLAYER1 -> "Please enter your ships",
    SHIP_PLAYER2 -> "Please enter your ships",
    SHOTS -> "Please enter your Shot",
    END -> "Thank you for playing Battleship-Game"
  )

  def message(gameState: GameState) = {
    map(gameState)
  }

}
