package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

import scala.language.postfixOps

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size) : Set[List[Figure]] =
    placeFigures(figuresToPlace, Square.generateChessboard(size.width, size.height), List[Figure]())
      .filterNot(_.isEmpty)

  def placeFigures(figuresToPlace: List[FigureType], availableSquares: Seq[Square], placedFigures: List[Figure]): Set[List[Figure]] = {
    if(figuresToPlace.isEmpty) Set(placedFigures.sorted)
    else {
      for {
        f <- findPlaceForFigure(figuresToPlace.head, availableSquares, placedFigures)
        p <- placeFigures(
          figuresToPlace.tail,
          availableSquares diff List(f.thisSquare) filter f.doesntBeatOn,
          f :: placedFigures
        )
      } yield p
    }
  }

  def findPlaceForFigure(figureType: FigureType, squares: Seq[Square], figures: List[Figure]): Set[Figure] = {
    squares map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toSet
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