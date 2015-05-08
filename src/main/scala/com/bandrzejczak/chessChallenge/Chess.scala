package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

import scala.language.postfixOps

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size) : Set[List[Figure]] = figuresToPlace match {
    case Nil => Set()
    case _ =>
      val chessboard = Square.generateChessboard(size.width, size.height)
      placeFigures(figuresToPlace, chessboard, List[Figure](), List[Figure]())
  }

  def placeFigures(figuresToPlace: List[FigureType], availableSquares: Seq[Square], placedFigures: List[Figure], usedPlacings: List[Figure]): Set[List[Figure]] = {
    val x = findPlaceForFigure(figuresToPlace.head, availableSquares, placedFigures)
    val availablePlacings = x filterNot {
      f => usedPlacings.contains(f)
    }
    placeEachFigure(availablePlacings, figuresToPlace.tail, availableSquares, placedFigures, Set[List[Figure]]())
  }

  def findPlaceForFigure(figureType: FigureType, squares: Seq[Square], figures: List[Figure]): List[Figure] = {
    squares map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList
  }

  def placeEachFigure(figures: List[Figure], figuresToPlace: List[FigureType], availableSquares: Seq[Square], placedFigures: List[Figure], usedPlacings: Set[List[Figure]]): Set[List[Figure]] = figures match {
    case Nil => Set[List[Figure]]()
    case f :: fs =>
      val newPlacings = if(figuresToPlace.isEmpty){
        if(f doesntBeatAny placedFigures) Set(f :: placedFigures) else Set[List[Figure]]()
      } else {
        placeFigures(
          figuresToPlace,
          availableSquares diff List(f.thisSquare) filter f.doesntBeatOn,
          f :: placedFigures,
          (usedPlacings filter (d => (f :: placedFigures).filter(_.figureType == f.figureType).forall(d.contains(_)))).flatten.toList
        )
      }
      placeEachFigure(fs, figuresToPlace, availableSquares, placedFigures, newPlacings.map(a => a.filter(_.figureType == f.figureType)) ++ usedPlacings) ++ newPlacings
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