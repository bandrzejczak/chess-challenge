package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.FigureType.FigureType
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.{implicitConversions, postfixOps}

class Chess(s: Size){

  implicit private val size = s
  
  def place(figuresToPlace: List[FigureType]) : Solutions = {
    val chessboard = Square.generateChessboard(size.width, size.height)
    val t1 = System.currentTimeMillis
    val fundamentalSolutions = placeFigures(figuresToPlace, chessboard, List[Figure]())
    val t2 = System.currentTimeMillis
    println(s"Finding fundamental solutions: ${t2 - t1}ms")
    val solutions = fundamentalSolutions.par.flatMap {
      s => s generateUniqueVariants
    } distinct
    val t3 = System.currentTimeMillis
    println(s"Generating variations: ${t3 - t2}ms")
    solutions.toList
  }

  private def findPlaceForFigure(figureType: FigureType, chessboard: Squares, figures: List[Figure]): List[Figure] = {
    filterVariations(figures)(chessboard.untried map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    } toList)
  }

  private def filterVariations(placedFigures: List[Figure])(figures: List[Figure]): List[Figure] = figures match {
    case Nil => Nil
    case f :: fs =>
      val placingsVariants = (f :: placedFigures).generateVariants
      val variantsForUnchangedPlacedFigures = placingsVariants.filter(_.tail hasAllElementsContainedIn placedFigures)
      val figureVariants = variantsForUnchangedPlacedFigures.map(_.head)
      f :: filterVariations(placedFigures)(fs filterNot figureVariants.contains)
  }

  private def placeFigures(figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Solutions =
    figuresToPlace match {
      case Nil => Solutions()
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

  private def generateSolutions(figure: Figure, figuresToPlace: List[FigureType], chessboard: Squares, placedFigures: List[Figure]): Solutions = {
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
      List(figure :: placedFigures)
    else
      Solutions()
  }
}

object Chess {
  type Solutions = List[List[Figure]]
  def Solutions() = List[List[Figure]]()
}

object FigureType extends Enumeration {
  type FigureType = Value
  val King, Queen, Bishop, Rook, Knight = Value
}

case class Size(width: Int, height: Int)