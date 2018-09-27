package com.tuco.draughts;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.StandardBoardCreator;

/**
 * Hello world!
 *
 */
class App
{
    public static void main( String[] args )
    {
        Board board = new Board(new StandardBoardCreator());
        System.out.printf(board.toString());
    }
}
