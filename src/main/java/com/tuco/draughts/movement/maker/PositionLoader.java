package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;

public interface PositionLoader {
    Coordinate loadPositionFromUser();
}