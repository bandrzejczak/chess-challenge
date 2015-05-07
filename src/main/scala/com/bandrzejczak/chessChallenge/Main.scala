package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Size._

object Main extends App {
  val figures = Chess.place(
    List(
      FigureType.Queen,
      FigureType.Queen,
      FigureType.Bishop,
      FigureType.Bishop,
      FigureType.Knight,
      FigureType.King,
      FigureType.King
  ),
    7 x 7
  )
  println(figures.size)
}
