package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

package object implicits {
  implicit class FiguresList(list: List[Figure]) {
    def withFigureTypeSameAs(figure: Figure) = withFigureTypeEqual(figure.figureType)
    def withFigureTypeEqual(figureType: FigureType) = list filter (a => figureType == a.figureType)
    def hasAllElementsContainedIn(otherList: List[Figure]) = list forall (otherList.contains(_))
  }

  implicit class SolutionsSet(solutions: Set[List[Figure]]) {
    def withFigureTypeSameAs(figure: Figure) = solutions map (solution => solution withFigureTypeSameAs figure)
  }

  implicit class SquaresSeq(seq: Seq[Square]) {
    def leftAfterPlacing(figure: Figure) = seq diff List(figure.thisSquare) filter figure.doesntBeatOn
  }
}
