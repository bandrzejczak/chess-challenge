package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.implicits.FiguresList

import scala.language.implicitConversions

class Squares(val safe: Seq[Square], private val triedCombinations: Set[List[Figure]]) {
  def untried: Seq[Square] = {
    val triedSquares = triedCombinations.flatten.map(_.thisSquare)
    safe filterNot triedSquares.contains
  }

  def leftAfterPlacing(figures: List[Figure]) = {
    val newSafe = safe leftAfterPlacing figures.head
    val newTriedCombinations = triedCombinations filter (d => figures.withFigureTypeSameAs(figures.head).hasAllElementsContainedIn(d))
    new Squares(newSafe, newTriedCombinations)
  }

  def addTried(relevantSolutions: Set[List[Figure]]) = {
    val newTriedCombinations = triedCombinations ++ relevantSolutions
    new Squares(safe, newTriedCombinations)
  }

  implicit class SquaresSeq(seq: Seq[Square]) {
    def leftAfterPlacing(figure: Figure): Seq[Square] = seq diff List(figure.thisSquare) filter figure.doesntBeatOn
  }
}

object Squares {
  def apply(squares: Seq[Square]) = new Squares(squares, Set[List[Figure]]())
}
