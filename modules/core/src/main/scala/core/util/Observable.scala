package core.util

trait Observer {
  def update: Unit
}

trait Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(_ == s)

  def notifyObservers: Unit = subscribers.foreach(_.update)
}