package com.bandrzejczak.chessChallenge

case class Square(x: Int, y: Int) {
  def isAcrossOf(that: Square) : Boolean =
    Math.abs(y-that.y) == Math.abs(x-that.x)
}

object Square {
  def generateChessboard(width: Int, height: Int): Seq[Square] = {
    for {
      x <- 1 to width
      y <- 1 to height
    } yield Square(x, y)
  }
}
