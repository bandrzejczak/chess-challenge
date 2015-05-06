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
      val availablePlacings = findPlaceForFigure(figuresToPlace.head, availableSquares, placedFigures)
      placeEachFigure(availablePlacings, figuresToPlace.tail, availableSquares, placedFigures)
    }
  }

  def findPlaceForFigure(figureType: FigureType, squares: Seq[Square], figures: List[Figure]): List[Figure] = {
    squares map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList
  }

  def placeEachFigure(figures: List[Figure], figuresToPlace: List[FigureType], availableSquares: Seq[Square], placedFigures: List[Figure]): Set[List[Figure]] = figures match {
    case Nil => Set[List[Figure]]()
    case f :: fs =>
      val a = placeFigures(
        figuresToPlace,
        availableSquares diff List(f.thisSquare) filter f.doesntBeatOn,
        f :: placedFigures
      )
      placeEachFigure(fs, figuresToPlace, availableSquares, placedFigures) ++ a
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