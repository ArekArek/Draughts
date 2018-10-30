package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;

import java.util.List;

public interface HumanMovementInformator {
    void choosePosition(List<Coordinate> possiblePositions);

    void wrongPositionChosen();
}