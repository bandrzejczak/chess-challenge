package com.bandrzejczak.chessChallenge

import org.scalatest.{Matchers, WordSpec}
import Size._

class ChessSpec extends WordSpec with Matchers {


  "Placing algorithm" should {
    "find placing for 1x1 chessboard" in {
      //when
      val figures = Chess.place(List(FigureType.King), 1 x 1)
      //then
      figures should contain only King(1, 1)
    }
  }

}

