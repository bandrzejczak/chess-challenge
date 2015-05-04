package com.bandrzejczak.chessChallenge

import org.scalatest.{Matchers, WordSpec}

class PiecesSpec extends WordSpec with Matchers {

  "A king" should {
    "beat only on closest squares" in {
      val king = King(5, 3)
      assert(king beatsOn Square(5, 4))
    }

    "not beat on farther squares" in {
      val king = King(3, 7)
      assert(king doesntBeatOn Square(4, 5))
    }
  }

  "A queen" should {
    "beat in x axis" in {
      val queen = Queen(4, 4)
      for(x <- 1 to 8)
        assert(queen beatsOn Square(x, 4))
    }

    "beat in y axis" in {
      val queen = Queen(4, 4)
      for(y <- 1 to 8)
        assert(queen beatsOn Square(4, y))
    }

    "beat across" in {
      val queen = Queen(4, 6)
      for ((x, y) <- (1 to 6) zip (3 to 8))
        assert(queen beatsOn Square(x,y))
      for ((x, y) <- (2 to 7) zip (3 to 8).reverse)
        assert(queen beatsOn Square(x,y))
    }

    "not beat in other squares" in {
      val queen = Queen(6, 4)
      assert(queen doesntBeatOn Square(3, 3))
    }
  }

}

sealed trait Figure {
  val x: Int
  val y: Int

  def beatsOn(square: Square) : Boolean
  def doesntBeatOn(square: Square) : Boolean = !beatsOn(square)
}

case class King(x: Int, y: Int) extends Figure {
  override def beatsOn(square: Square) : Boolean =
    Math.abs(x - square.x) <= 1 && Math.abs(y - square.y) <= 1
}

case class Queen(x: Int, y: Int) extends Figure {
  override def beatsOn(square: Square): Boolean =
    y == square.y || x == square.x || (Math.abs(y-square.y) == Math.abs(x-square.x))
}