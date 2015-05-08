package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.FigureType.FigureType

import scala.language.implicitConversions

package object implicits {

  implicit class SizeBuilder(width: Int){
    def x(height: Int) = Size(width, height)
  }

  implicit def squaresListToSquares(squares: Seq[Square]): Squares = Squares(squares)

  implicit class FiguresList(list: List[Figure]) {
    def withFigureTypeSameAs(figure: Figure): List[Figure] = withFigureTypeEqual(figure.figureType)
    def withFigureTypeEqual(figureType: FigureType): List[Figure] = list filter (a => figureType == a.figureType)
    def hasAllElementsContainedIn(otherList: List[Figure]): Boolean = list forall (otherList.contains(_))
  }

  implicit class SolutionsSet(solutions: Solutions) {
    def withFigureTypeSameAs(figure: Figure): Solutions =
      solutions map (solution => solution withFigureTypeSameAs figure)
  }

}
