package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

sealed trait Figure {
  val x: Int
  val y: Int
  lazy val thisSquare = Square(x, y)

  def beatsOn(thatSquare: Square) : Boolean
  def doesntBeatOn(thatSquare: Square) : Boolean = !beatsOn(thatSquare)
}

object Figure {
  def fromType(figureType: FigureType, position: Square): Figure = figureType match {
    case FigureType.King => King(position.x, position.y)
    case FigureType.Queen => Queen(position.x, position.y)
    case FigureType.Bishop => Bishop(position.x, position.y)
    case FigureType.Rook => Bishop(position.x, position.y)
    case FigureType.Knight => Knight(position.x, position.y)
  }
}

case class King(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square) : Boolean =
    Math.abs(x - thatSquare.x) <= 1 && Math.abs(y - thatSquare.y) <= 1
}

case class Queen(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean =
    (thisSquare isAlignedWith thatSquare) || (thatSquare isAcrossOf thisSquare)
}

case class Bishop(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean =
    thatSquare isAcrossOf thisSquare
}

case class Rook(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean =
    thisSquare isAlignedWith thatSquare
}

case class Knight(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean = {
    val xDiff = Math.abs(x - thatSquare.x)
    val yDiff = Math.abs(y - thatSquare.y)
    xDiff + yDiff == 3 && Math.abs(xDiff - yDiff) == 1
  }
}