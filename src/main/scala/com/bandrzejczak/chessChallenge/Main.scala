package com.bandrzejczak.chessChallenge

import com.bandrzejczak.chessChallenge.Chess.Solutions
import com.bandrzejczak.chessChallenge.implicits._

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object Main extends App {

  val welcomeMessage =
    """Hi!
      |
      |Welcome to ultimate chess challenge solver.
      |The challenge is to find all unique setups of figures on a chessboard
      |with size M x N, so that they don't beat each other.
      |
      |Please provide initial information below.""".stripMargin

  def readChessboardSize: Size = {
    print("Chessboard width: ")
    val width = StdIn.readInt()
    print("Chessboard height: ")
    val height = StdIn.readInt()
    width x height
  }

  val figuresPrompt =
    """Here are the figures and their keys:
      |King   => K
      |Queen  => Q
      |Bishop => B
      |Rook   => R
      |Knight => k
      |
      |What figures would you like to place on the chessboard?
      |Please, provide the keys and finish with an empty line:""".stripMargin

  def readFigures = Try {
    println(figuresPrompt)
    Stream.continually(Try(StdIn.readChar()))
      .takeWhile(_.isSuccess)
      .map {
        _.get match {
          case 'K' => FigureType.King
          case 'Q' => FigureType.Queen
          case 'B' => FigureType.Bishop
          case 'R' => FigureType.Rook
          case 'k' => FigureType.Knight
          case c => throw new UnrecognizedCharacterException(c)
        }
      }
      .toList
  }

  case class UnrecognizedCharacterException(c: Char) extends Throwable

  def compute(figures: List[FigureType.FigureType], size: Size): Unit = {
    val startTime = System.currentTimeMillis()
    val solutions = Chess.place(figures, size)
    val endTime = System.currentTimeMillis()
    println(s"The algorithm found ${solutions.size} solutions")
    println(s"This solution was generated for you in ${endTime - startTime}ms")
    print("Would you like to see the solutions? (n/Y) ")
    Try(StdIn.readChar().toLower) match {
      case Success('y') => printSolutions(solutions, size)
      case Failure(_: StringIndexOutOfBoundsException) => printSolutions(solutions, size)
      case _ => //fin
    }
  }

  def printSolutions(solutions: Solutions, size: Size): Unit = {
    val a: List[String] = for {
      solution <- solutions.toList
      y <- 1 to size.height
      x <- 1 to size.width
    } yield {
      solution.find(_.thisSquare == Square(x,y)) match {
        case Some(King(_,_)) => "K"
        case Some(Queen(_,_)) => "Q"
        case Some(Bishop(_,_)) => "B"
        case Some(Rook(_,_)) => "R"
        case Some(Knight(_,_)) => "k"
        case None => "."
      }
    }
    a grouped (size.width * size.height) foreach {
      s =>
        println()
        s grouped size.width foreach (l => println(l.mkString(" ")))
    }
  }

  println(welcomeMessage)
  val size = readChessboardSize
  readFigures match {
    case Failure(UnrecognizedCharacterException(c)) => println(s"Unrecognized character: $c")
    case Failure(t) => println(s"Computation failed with exception $t")
    case Success(figures) => compute(figures, size)
  }
}
