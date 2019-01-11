package com.tuco.draughts.game.util;

import com.tuco.draughts.movement.maker.AIMovementDescription;
import com.tuco.draughts.movement.util.Movement;

public interface ChangeTurnListener {

    default void beforeTurn() {
    }

    default void afterTurn(Movement movement) {
    }

    default void afterAITurn(AIMovementDescription movementDescription) {
    }
}