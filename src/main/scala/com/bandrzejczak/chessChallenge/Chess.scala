package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.{implicitConversions, postfixOps}

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size) : Set[List[Figure]] = {
    val chessboard = Square.generateChessboard(size.width, size.height)
    placeFigures(figuresToPlace, chessboard, List[Figure]())
  }

  def findPlaceForFigure(figureType: FigureType, squares: Squares, figures: List[Figure]): List[Figure] = {
    squares.untried map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList
  }

  def placeFigures(figuresToPlace: List[FigureType], squares: Squares, placedFigures: List[Figure]): Set[List[Figure]] =
    figuresToPlace match {
      case Nil => Set()
      case figure :: figuresRemainder =>
        val availablePlacings = findPlaceForFigure(figure, squares, placedFigures)
        investigatePlacings(availablePlacings, figuresRemainder, squares.safe, placedFigures)
    }

  def investigatePlacings(figures: List[Figure], figuresToPlace: List[FigureType], squares: Squares, placedFigures: List[Figure]): Set[List[Figure]] =
    figures match {
      case Nil => Set[List[Figure]]()
      case f :: fs =>
        val newPlacedFigures = f :: placedFigures
        val newPlacings = if(figuresToPlace.isEmpty){
          if(f doesntBeatAny placedFigures) Set(newPlacedFigures) else Set[List[Figure]]()
        } else {
          placeFigures(
            figuresToPlace,
            squares leftAfterPlacing newPlacedFigures,
            newPlacedFigures
          )
        }
        investigatePlacings(fs, figuresToPlace, squares addTried (newPlacings withFigureTypeSameAs f), placedFigures) ++ newPlacings
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