package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.FigureType.FigureType

object Chess {
  def place(figuresToPlace: List[FigureType], size: Size) : List[Figure] = figuresToPlace match {
    case f :: Nil => List(Figure.fromType(f, Square(1, 1)))
    case _ => Nil
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