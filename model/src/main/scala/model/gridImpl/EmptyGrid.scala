package model.gridImpl

class EmptyGrid(size: Int) extends GridTemplate {
  def fullField: String = field(width, size)

  override def toString: String = field(width, size)
}
