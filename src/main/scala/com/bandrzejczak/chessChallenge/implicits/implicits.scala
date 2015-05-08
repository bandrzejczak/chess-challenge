package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.FigureType.FigureType

package object implicits {
  implicit class FiguresList(list: List[Figure]) {
    def withFigureTypeSameAs(figure: Figure): List[Figure] = withFigureTypeEqual(figure.figureType)
    def withFigureTypeEqual(figureType: FigureType): List[Figure] = list filter (a => figureType == a.figureType)
    def hasAllElementsContainedIn(otherList: List[Figure]): Boolean = list forall (otherList.contains(_))
  }

  implicit class SolutionsSet(solutions: Solutions) {
    def withFigureTypeSameAs(figure: Figure): Solutions =
      solutions map (solution => solution withFigureTypeSameAs figure)
  }

  implicit class SquaresSeq(seq: Seq[Square]) {
    def leftAfterPlacing(figure: Figure): Seq[Square] = seq diff List(figure.thisSquare) filter figure.doesntBeatOn
  }


  implicit class ChessboardRotations(figures: List[Figure]) {
    def rotate90deg(size: Size) = {
      figures map (_.rotate90deg(size))
    }

    def rotate180deg(size: Size) = {
      figures map (_.rotate180deg(size))
    }

    def reflect(size: Size) = {
      figures map (_.reflect(size))
    }

    implicit class FiguresRotations(figure: Figure) {
      def rotate90deg(size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(y, height + 1 - x))
      }

      def rotate180deg(size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(width + 1 - x, height + 1 - y))
      }

      def reflect(size: Size) = {
        import figure.thisSquare._
        import size._
        Figure.fromType(figure.figureType, Square(width + 1 - x, y))
      }
    }
  }

  implicit def fromFigures(figures: List[Figure]): FundamentalSolution = new FundamentalSolution(figures)
  
}
