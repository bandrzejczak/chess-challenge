# Chess Challenge
## Bartek Andrzejczak

**The challenge is to find all unique setups of figures on a chessboard with size M x N, so that they don't beat each other.**

### How to build it?

    sbt assembly

### How to run it?

    java -jar -Xmx3g target/scala-2.11/chess-challenge-assembly-1.0.jar

### How will the output look like? 

    Hi!

    Welcome to ultimate chess challenge solver.
    The challenge is to find all unique setups of figures on a chessboard
    with size M x N, so that they don't beat each other.
    
    Please provide initial information below.
    Chessboard width: 3
    Chessboard height: 3
    Here are the figures and their keys:
    King   => K
    Queen  => Q
    Bishop => B
    Rook   => R
    Knight => k
    
    What figures would you like to place on the chessboard?
    Please, provide the keys and finish with an empty line:
    K
    K
    R
    
    The algorithm found 4 solutions
    This solution was generated for you in 24ms
    Would you like to see the solutions? (n/Y) 
    
    . . K
    R . .
    . . K
    
    . R .
    . . .
    K . K
    
    K . K
    . . .
    . R .
    
    K . .
    . . R
    K . .

    Press ENTER to continue...

### How does an execution for *ultimate challenge* looks like?

An *ultimate challenge* is a challenge of putting:

- 2 Queens
- 2 Kings
- 2 Bishops
- 1 Knight

on a chessboard of 7 x 7.

    Hi!
    
    Welcome to ultimate chess challenge solver.
    The challenge is to find all unique setups of figures on a chessboard
    with size M x N, so that they don't beat each other.
    
    Please provide initial information below.
    Chessboard width: 7
    Chessboard height: 7
    Here are the figures and their keys:
    King   => K
    Queen  => Q
    Bishop => B
    Rook   => R
    Knight => k
    
    What figures would you like to place on the chessboard?
    Please, provide the keys and finish with an empty line:
    Q
    Q
    K
    K
    B
    B
    k
    
    The algorithm found 3063828 solutions
    This solution was generated for you in 23839ms
    Would you like to see the solutions? (n/Y) n