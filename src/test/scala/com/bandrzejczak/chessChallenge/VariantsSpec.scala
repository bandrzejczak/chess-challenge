package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.implicits._
import org.scalatest.{FlatSpec, Matchers}

class VariantsSpec extends FlatSpec with Matchers {

  "List of figures" should "be rotated by 90 deg" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures rotate90deg (4 x 4)
    //then
    rotated should contain theSameElementsAs List(
      King(1,4), Rook(1, 2), Queen(4,4), Knight(2,3)
    )
  }

  it should "be rotated by 180 deg" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures rotate180deg (4 x 4)
    //then
    rotated should contain theSameElementsAs List(
      King(4,4), Rook(2,4), Queen(4,1), Knight(3,3)
    )
  }

  it should "be reflected in Y axis" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val rotated = figures reflect (4 x 4)
    //then
    rotated should contain theSameElementsAs List(
      King(4,1), Rook(2,1), Queen(4,4), Knight(3,2)
    )
  }

  it should "generate all 8 variants for fundamental solution on square chessboard" in {
    //given
    val figures = List(King(1,1), Rook(3,1), Queen(1,4), Knight(2,2))
    //when
    val variants = figures generateVariants (4 x 4)
    //then
    variants should contain theSameElementsAs List(
      List(King(1,1), Knight(2,2), Queen(1,4), Rook(3,1)),
      List(King(1,4), Knight(2,3), Queen(4,4), Rook(1,2)),
      List(King(4,4), Knight(3,3), Queen(4,1), Rook(2,4)),
      List(King(4,1), Knight(3,2), Queen(1,1), Rook(4,3)),
      List(King(4,1), Knight(3,2), Queen(4,4), Rook(2,1)),
      List(King(4,4), Knight(3,3), Queen(1,4), Rook(4,2)),
      List(King(1,4), Knight(2,3), Queen(1,1), Rook(3,4)),
      List(King(1,1), Knight(2,2), Queen(4,1), Rook(1,3))
    )
  }
  
  it should "generate 4 variants for x-symmetric fundamental solution" in {
    //given
    val figures = List(King(1,1), King(1,3), Rook(3,2))
    //when
    val variants = figures generateVariants (3 x 3)
    //then
    variants should contain theSameElementsAs List(
      List(King(1,1), King(1,3), Rook(3,2)),
      List(King(1,1), King(3,1), Rook(2,3)),
      List(King(1,3), King(3,3), Rook(2,1)),
      List(King(3,1), King(3,3), Rook(1,2))
    )
  }
  
  it should "generate 2 variants for across-symmetric fundamental solution" in {
    //given
    val figures = List(Rook(1,1), Rook(2,2))
    //when
    val variants = figures generateVariants (2 x 2)
    //then
    variants should contain theSameElementsAs List(
      List(Rook(1,1), Rook(2,2)),
      List(Rook(1,2), Rook(2,1))
    )
  }

}
