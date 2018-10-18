package com.tuco.draughts.movement.maker;

import com.tuco.draughts.movement.util.Movement;

import java.util.List;

public interface HumanMovementInformator {
    void choosePosition(List<Movement> movements);

    void wrongPositionChosen();
}