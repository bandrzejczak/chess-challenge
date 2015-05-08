package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Size._
import com.bandrzejczak.chessChallenge.implicits._
import org.scalatest.{FlatSpec, Matchers}

class VariantsSpec extends FlatSpec with Matchers {

  "List of figures" should "be rotated by 90 deg" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures rotate90deg (4 x 4)
    //then
    rotated should contain only (
      King(1,4), Rook(1, 2), Queen(4,4), Knight(2,3)
    )
  }

  it should "be rotated by 180 deg" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures rotate180deg (4 x 4)
    //then
    rotated should contain only (
      King(4,4), Rook(2,4), Queen(4,1), Knight(3,3)
    )
  }

  it should "be reflected in Y axis" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures reflect (4 x 4)
    //then
    rotated should contain only (
      King(4,1), Rook(2,1), Queen(4,4), Knight(3,2)
    )
  }

  it should "generate all 8 variants for fundamental solution on square chessboard" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val variants = figures generateVariants (4 x 4)
    //then
    variants should contain only (
      List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2)),
      List(King(1,4), Rook(1,2), Queen(4,4), Knight(2,3)),
      List(King(4,4), Rook(2,4), Queen(4,1), Knight(3,3)),
      List(King(4,1), Rook(4,3), Queen(1,1), Knight(3,2)),
      List(King(4,1), Rook(2,1), Queen(4,4), Knight(3,2)),
      List(King(4,4), Rook(4,2), Queen(1,4), Knight(3,3)),
      List(King(1,4), Rook(3,4), Queen(1,1), Knight(2,3)),
      List(King(1,1), Rook(1,3), Queen(4,1), Knight(2,2))
    )
  }

}
