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

  "A bishop" should {
    "beat across" in {
      val bishop = Bishop(6, 6)
      for ((x, y) <- (1 to 8) zip (1 to 8))
        assert(bishop beatsOn Square(x,y))
      for ((x, y) <- (4 to 8) zip (4 to 8).reverse)
        assert(bishop beatsOn Square(x,y))
    }

    "not beat in other squares" in {
      val bishop = Bishop(6, 4)
      assert(bishop doesntBeatOn Square(1, 4))
    }
  }

}

sealed trait Figure {
  val x: Int
  val y: Int
  lazy val thisSquare = Square(x, y)

  def beatsOn(thatSquare: Square) : Boolean
  def doesntBeatOn(thatSquare: Square) : Boolean = !beatsOn(thatSquare)
}

case class King(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square) : Boolean =
    Math.abs(x - thatSquare.x) <= 1 && Math.abs(y - thatSquare.y) <= 1
}

case class Queen(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean =
    y == thatSquare.y || x == thatSquare.x || (thatSquare isAcrossOf thisSquare)
}

case class Bishop(x: Int, y: Int) extends Figure {
  override def beatsOn(thatSquare: Square): Boolean =
    thatSquare isAcrossOf thisSquare
}