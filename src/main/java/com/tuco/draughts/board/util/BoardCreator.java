package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Place;

public interface BoardCreator {

    Place[][] createBoard();

    int getBoardSize();
}