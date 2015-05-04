package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

object Chess {
  def place(figuresToPlace: List[FigureType], size: Size) : List[Figure] =
    placeFigures(figuresToPlace, Square.generateChessboard(size.width, size.height), List[Figure]())

  def placeFigures(figuresToPlace: List[FigureType], emptySquares: Seq[Square], placedFigures: List[Figure]): List[Figure] = {
    figuresToPlace match {
      case Nil => placedFigures
      case f :: fs =>
        if(emptySquares.isEmpty) throw new IllegalStateException
        val figure = Figure.fromType(f, emptySquares.head)
        if(figure doesntBeatAny placedFigures)
          placeFigures(fs, emptySquares.tail filter figure.doesntBeatOn, figure :: placedFigures)
        else
          placeFigures(figuresToPlace, emptySquares.tail, placedFigures)
    }
  }
}

object FigureType extends Enumeration {
  type FigureType = Value
  val King, Queen, Bishop, Rook, Knight = Value
}

case class Size(width: Int, height: Int)

object Size {
  implicit class SizeBuilder(width: Int){
    def x(height: Int) = Size(width, height)
  }
}