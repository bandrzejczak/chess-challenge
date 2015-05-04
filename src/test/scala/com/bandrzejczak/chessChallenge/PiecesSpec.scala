package com.bandrzejczak.chessChallenge

import org.scalatest.{Matchers, WordSpec}

class PiecesSpec extends WordSpec with Matchers {

  "A king" should {
    "beat only on closest squares" in {
      val king = King(5, 3)
      assert(king beatsOn Square(5, 4))
    }

    "not beat on farther squares" in {
      val king = King(3,7)
      assert(king doesntBeatOn Square(4,5))
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
