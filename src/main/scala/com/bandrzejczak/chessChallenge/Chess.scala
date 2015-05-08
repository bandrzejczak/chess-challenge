package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.{implicitConversions, postfixOps}

object Chess {
  
  type Solutions = Set[List[Figure]]
  def Solutions() = Set[List[Figure]]()

  def place(figuresToPlace: List[FigureType], size: Size) : Solutions = {
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

  def placeFigures(figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Solutions =
    figuresToPlace match {
      case Nil => Set()
      case figure :: figuresRemainder =>
        val availablePlacings = findPlaceForFigure(figure, chessboard, placedFigures)
        investigatePlacings(availablePlacings, figuresRemainder, chessboard.safe, placedFigures)
    }

  def investigatePlacings(figures: List[Figure], figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Solutions =
    figures match {
      case Nil => Solutions()
      case f :: fs =>
        val solutions = generateSolutions(f, figuresToPlace, chessboard, placedFigures)
        investigatePlacings(fs, figuresToPlace, chessboard addTried (solutions withFigureTypeSameAs f), placedFigures) ++ solutions
    }

  def generateSolutions(figure: Figure, figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Set[List[Figure]] = {
    if (figuresToPlace.isEmpty)
      generateSingleSolution(figure, placedFigures)
    else
      placeFigures(
        figuresToPlace,
        chessboard leftAfterPlacing (figure :: placedFigures),
        figure :: placedFigures
      )
  }

  def generateSingleSolution(figure: Figure, placedFigures: List[Figure]): Solutions = {
    if (figure doesntBeatAny placedFigures)
      Set(figure :: placedFigures)
    else
      Solutions()
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