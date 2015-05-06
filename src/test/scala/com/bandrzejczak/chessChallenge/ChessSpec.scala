package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Size._
import org.scalatest.{Matchers, WordSpec}

class ChessSpec extends WordSpec with Matchers {

  "Placing algorithm" should {
    "not place any figures based on empty figure list" in {
      //when
      val figures = Chess.place(List(), 1 x 1)
      //then
      figures shouldBe empty
    }

    "find placing for 1x1 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.King), 1 x 1)
      //then
      figures.head should contain only King(1, 1)
    }

    "return throw exception for unsolvable problem" in {
      //when
      val figures = Chess.place(List(FigureType.King, FigureType.Bishop), 1 x 1)
      //then
      figures shouldBe empty
    }

    "place two rooks on 2x2 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.Rook, FigureType.Rook), 2 x 2)
      //then
      figures should contain allOf(
        List(Rook(1, 1), Rook(2, 2)),
        List(Rook(1, 2), Rook(2, 1))
        )
    }

    "place two kings and a rook on 3x3 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.King, FigureType.King, FigureType.Rook), 3 x 3)
      //then
      figures shouldBe Set(
        List(King(1, 1), King(1, 3), Rook(3, 2)),
        List(King(1, 1), King(3, 1), Rook(2, 3)),
        List(King(3, 1), King(3, 3), Rook(1, 2)),
        List(King(1, 3), King(3, 3), Rook(2, 1))
      )
    }

    "place two rooks and four knights on 4x4 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.Rook, FigureType.Rook, FigureType.Knight, FigureType.Knight, FigureType.Knight, FigureType.Knight), 4 x 4)
      //then
      figures shouldBe Set(
        List(Knight(1, 1), Knight(1, 3), Knight(3, 1), Knight(3, 3), Rook(2, 2), Rook(4, 4)),
        List(Knight(2, 1), Knight(2, 3), Knight(4, 1), Knight(4, 3), Rook(1, 2), Rook(3, 4)),
        List(Knight(2, 2), Knight(2, 4), Knight(4, 2), Knight(4, 4), Rook(1, 1), Rook(3, 3)),
        List(Knight(2, 1), Knight(2, 3), Knight(4, 1), Knight(4, 3), Rook(1, 4), Rook(3, 2)),
        List(Knight(1, 2), Knight(1, 4), Knight(3, 2), Knight(3, 4), Rook(2, 1), Rook(4, 3)),
        List(Knight(1, 2), Knight(1, 4), Knight(3, 2), Knight(3, 4), Rook(2, 3), Rook(4, 1)),
        List(Knight(1, 1), Knight(1, 3), Knight(3, 1), Knight(3, 3), Rook(2, 4), Rook(4, 2)),
        List(Knight(2, 2), Knight(2, 4), Knight(4, 2), Knight(4, 4), Rook(1, 3), Rook(3, 1))
      )
    }
  }

}

