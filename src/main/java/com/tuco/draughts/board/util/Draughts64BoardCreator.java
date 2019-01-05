package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;

public class Draughts64BoardCreator implements BoardCreator {

    private static final int BOARD_SIZE = 8;

    @Override
    public Chequer[][] createBoard() {
        Chequer[][] board = new Chequer[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j % 2) % 2 != 0)
                    board[i][j] = Chequer.DISABLED;
                else {
                    if (j < 3)
                        board[i][j] = Chequer.WHITE;
                    else if (j > 4)
                        board[i][j] = Chequer.BLACK;
                    else
                        board[i][j] = Chequer.EMPTY;
                }
            }
        }
        return board;
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}