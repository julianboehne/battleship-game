package persistency.DB

import persistency.*

trait DAOInterface:
  def save(gameData: GameData): Unit
  def load(): GameData

