package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

object Chess {

  def place(figuresToPlace: List[FigureType], size: Size) : Set[List[Figure]] =
    placeFigures(figuresToPlace, Square.generateChessboard(size.width, size.height), List[Figure]())

  def findPlaceForFigure(figureType: FigureType, squares: Seq[Square], figures: List[Figure]): Seq[Figure] = {
    squares map {
      Figure.fromType(figureType, _)
    } filter {
      f => f doesntBeatAny figures
    }
  }

  /*
  * TODO Create a cut-off for permutations of existing results,
  * which will allow to switch back from Set to List
  */
  def placeFigures(figuresToPlace: List[FigureType], availableSquares: Seq[Square], placedFigures: List[Figure]): Set[List[Figure]] = {
    figuresToPlace match {
      case Nil => if(placedFigures.nonEmpty) Set(placedFigures.sorted) else Set()
      case f :: fs =>
        if(availableSquares.isEmpty) Set()
        else {
          findPlaceForFigure(f, availableSquares, placedFigures) match {
            case Nil => Set()
            case figures =>
              figures.foldLeft(Set[List[Figure]]()){
                (result, branch) =>
                  result ++ placeFigures(
                    fs,
                    availableSquares diff List(branch.thisSquare) filter branch.doesntBeatOn,
                    branch :: placedFigures
                  )
              }
          }
        }
    }
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