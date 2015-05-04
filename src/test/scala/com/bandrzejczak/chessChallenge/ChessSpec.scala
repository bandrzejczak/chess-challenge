package com.bandrzejczak.chessChallenge

import org.scalatest.{Matchers, WordSpec}
import Size._

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
      figures should contain only King(1, 1)
    }

    "return throw exception for unsolvable problem" in {
      intercept[IllegalStateException] {
        val figures = Chess.place(List(FigureType.King, FigureType.Bishop), 1 x 1)
      }
    }

    "place two rooks on 2x2 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.Rook, FigureType.Rook), 2 x 2)
      //then
      figures should contain only (Rook(1,1), Rook(2,2))
    }

    "place two kings and a rook on 3x3 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.King, FigureType.King, FigureType.Rook), 3 x 3)
      //then
      figures should contain only (King(1,1), King(1,3), Rook(3,2))
    }
  }

}

