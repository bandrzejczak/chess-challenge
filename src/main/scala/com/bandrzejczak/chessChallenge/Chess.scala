package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.FigureType.FigureType
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.{implicitConversions, postfixOps}

class Chess(size: Size) {

  def place(figuresToPlace: List[FigureType]) : Solutions = {
    val chessboard = Square.generateChessboard(size.width, size.height)
    placeFigures(figuresToPlace, chessboard, List[Figure]())
  }

  private def findPlaceForFigure(figureType: FigureType, chessboard: Squares, figures: List[Figure]): List[Figure] = {
    chessboard.untried map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList
  }

  private def placeFigures(figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Solutions =
    figuresToPlace match {
      case Nil => Set()
      case figure :: figuresRemainder =>
        val availablePlacings = findPlaceForFigure(figure, chessboard, placedFigures)
        investigatePlacings(availablePlacings, figuresRemainder, chessboard.safe, placedFigures, Solutions())
    }

  private def investigatePlacings(figures: List[Figure], figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure], solutions: Solutions): Solutions =
    figures match {
      case Nil => solutions
      case f :: fs =>
        val newSolutions = generateSolutions(f, figuresToPlace, chessboard, placedFigures)
        investigatePlacings(fs, figuresToPlace, chessboard addTried (newSolutions withFigureTypeSameAs f), placedFigures, solutions ++ newSolutions)
    }

  private def generateSolutions(figure: Figure, figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Set[List[Figure]] = {
    if (figuresToPlace.isEmpty)
      generateSingleSolution(figure, placedFigures)
    else
      placeFigures(
        figuresToPlace,
        chessboard leftAfterPlacing (figure :: placedFigures),
        figure :: placedFigures
      )
  }

  private def generateSingleSolution(figure: Figure, placedFigures: List[Figure]): Solutions = {
    if (figure doesntBeatAny placedFigures)
      Set(figure :: placedFigures)
    else
      Solutions()
  }
}

object Chess {
  type Solutions = Set[List[Figure]]
  def Solutions() = Set[List[Figure]]()
}

object FigureType extends Enumeration {
  type FigureType = Value
  val King, Queen, Bishop, Rook, Knight = Value
}

case class Size(width: Int, height: Int)