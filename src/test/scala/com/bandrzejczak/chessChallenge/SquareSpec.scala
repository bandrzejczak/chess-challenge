package com.bandrzejczak.chessChallenge

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

class SquareSpec extends FlatSpec with Matchers with PropertyChecks {

  def chessboardSizeGenerator = Gen.choose(1, 1000)

  "Square" should "be able to generate all squares available in a given chessboard" in {
    forAll (chessboardSizeGenerator, chessboardSizeGenerator){
      (x: Int, y: Int) =>
        //given
        val random = new Random()
        //when
        val squares = Square.generateChessboard(x, y)
        //then
        squares should have size (x*y)
        squares should contain (Square(random.nextInt(x) + 1, random.nextInt(y) + 1))
    }
  }

}


