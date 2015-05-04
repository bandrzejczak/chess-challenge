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

    "return empty list for unsolvable problem" in {
      //when
      val figures = Chess.place(List(FigureType.King, FigureType.Bishop), 1 x 1)
      //then
      figures shouldBe empty
    }
  }

}

