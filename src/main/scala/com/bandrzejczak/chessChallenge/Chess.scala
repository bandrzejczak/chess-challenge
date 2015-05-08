package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.{implicitConversions, postfixOps}

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size) : Set[List[Figure]] = {
    val chessboard = Square.generateChessboard(size.width, size.height)
    placeFigures(figuresToPlace, chessboard, List[Figure]())
  }

  def findPlaceForFigure(figureType: FigureType, chessboard: Squares, figures: List[Figure]): List[Figure] = {
    chessboard.untried map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList
  }

  def placeFigures(figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Set[List[Figure]] =
    figuresToPlace match {
      case Nil => Set()
      case figure :: figuresRemainder =>
        val availablePlacings = findPlaceForFigure(figure, chessboard, placedFigures)
        investigatePlacings(availablePlacings, figuresRemainder, chessboard.safe, placedFigures)
    }

  def investigatePlacings(figures: List[Figure], figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Set[List[Figure]] =
    figures match {
      case Nil => Set[List[Figure]]()
      case f :: fs =>
        val solutions = generateSolutions(f, figuresToPlace, chessboard, placedFigures)
        investigatePlacings(fs, figuresToPlace, chessboard addTried (solutions withFigureTypeSameAs f), placedFigures) ++ solutions
    }

  def generateSolutions(figure: Figure, figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Set[List[Figure]] = {
    if (figuresToPlace.isEmpty)
      generateSingleSolution(placedFigures, figure)
    else
      placeFigures(
        figuresToPlace,
        chessboard leftAfterPlacing (figure :: placedFigures),
        figure :: placedFigures
      )
  }

  def generateSingleSolution(placedFigures: List[Figure], f: Figure): Set[List[Figure]] = {
    if (f doesntBeatAny placedFigures)
      Set(f :: placedFigures)
    else
      Set[List[Figure]]()
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