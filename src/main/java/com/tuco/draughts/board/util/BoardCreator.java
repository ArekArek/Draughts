package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;

public interface BoardCreator {

    Chequer[][] createBoard();

    int getBoardSize();
}