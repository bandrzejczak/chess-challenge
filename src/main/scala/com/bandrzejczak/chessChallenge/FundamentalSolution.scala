package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.implicits._

import scala.language.implicitConversions

class FundamentalSolution(figures: List[Figure]) {

  def generateUniqueVariants(implicit size: Size): Solutions = {
    generateVariants.map(_.sorted).distinct
  }

  def generateVariants(implicit size: Size): Solutions = {
    rotate flatMap {
      r => List(r, r reflect)
    }
  }

  private def rotate(implicit size: Size): Solutions = {
    import size._
    if(width == height)
      rotatingBy90deg(figures).take(4).toList
    else
      List(figures, figures rotate180deg)
  }

  private def rotatingBy90deg(figures: List[Figure])(implicit size: Size): Stream[List[Figure]] =
    figures #:: rotatingBy90deg(figures rotate90deg)
}