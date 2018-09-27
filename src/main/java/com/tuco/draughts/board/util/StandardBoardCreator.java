package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Place;

public class StandardBoardCreator implements BoardCreator {

    private static final int BOARD_SIZE = 8;

    @Override
    public Place[][] createBoard() {
        Place[][] board = new Place[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j % 2) % 2 != 0)
                    board[i][j] = Place.DISABLED;
                else {
                    if (j < 3)
                        board[i][j] = Place.WHITE;
                    else if (j > 4)
                        board[i][j] = Place.BLACK;
                    else
                        board[i][j] = Place.EMPTY;
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