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

  implicit class ChessboardRotations(figures: List[Figure]) {
    def rotate90deg(implicit size: Size) = {
      figures map (_.rotate90deg)
    }

    def rotate180deg(implicit size: Size) = {
      figures map (_.rotate180deg)
    }

    def reflect(implicit size: Size) = {
      figures map (_.reflect)
    }

    private implicit class FiguresRotations(figure: Figure) {
      def rotate90deg(implicit size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(y, height + 1 - x))
      }

      def rotate180deg(implicit size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(width + 1 - x, height + 1 - y))
      }

      def reflect(implicit size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(width + 1 - x, y))
      }
    }
  }

  implicit def fromFigures(figures: List[Figure]): FundamentalSolution = new FundamentalSolution(figures)
  
}
