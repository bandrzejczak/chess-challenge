package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Size._

object Main extends App {
  val figures = Chess.place(
    List(
      FigureType.King,
      FigureType.King,
      FigureType.Queen,
      FigureType.Queen,
      FigureType.Bishop,
      FigureType.Bishop,
      FigureType.Knight
    ),
    7 x 7
  )
  println(figures.size)
}
