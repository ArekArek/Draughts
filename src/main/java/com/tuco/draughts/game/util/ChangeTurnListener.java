package com.tuco.draughts.game.util;

import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.movement.maker.AIMovementDescription;
import com.tuco.draughts.movement.util.Movement;

public interface ChangeTurnListener {

    default void beforeTurn(DraughtGameManager gameManager) {
    }

    default void afterTurn(Movement movement) {
    }

    default void afterAITurn(AIMovementDescription movementDescription) {
    }
}