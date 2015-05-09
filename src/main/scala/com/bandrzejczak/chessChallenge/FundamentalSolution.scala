package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.implicitConversions

class FundamentalSolution(figures: List[Figure]) {

  def generateUniqueVariants(size: Size): Solutions = {
    generateVariants(size).map(_.sorted).distinct
  }

  def generateVariants(size: Size): Solutions = {
    rotate(size) flatMap {
      r => List(r, r reflect size)
    }
  }

  private def rotate(size: Size): Solutions = {
    import size._
    if(width == height)
      rotatingBy90deg(figures, size).take(4).toList
    else
      List(figures, figures rotate180deg size)
  }

  private def rotatingBy90deg(figures: List[Figure], size: Size): Stream[List[Figure]] =
    figures #:: rotatingBy90deg(figures rotate90deg size, size)
}