package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size): Iterable[Seq[Figure]] = {
    figuresToPlace.foldLeft(Map[Seq[Figure], Seq[Square]](List[Figure]() -> Square.generateChessboard(size.width, size.height))) {
      (resultAggregator, figure) => {
        for {
          r <- resultAggregator
          p <- findPlaceForFigure(figure, r)
        } yield p
      }
    }  map (_._1)
  }

  def findPlaceForFigure(figureType: FigureType, squares: (Seq[Figure], Seq[Square])): Seq[(Seq[Figure], Seq[Square])] = {
    for{
      square <- squares._2
      figure = Figure.fromType(figureType, square)
      if figure doesntBeatAny squares._1
      availableSquares = squares._2 diff List(figure.thisSquare) filter figure.doesntBeatOn
    } yield (squares._1 :+ figure).sorted -> availableSquares
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