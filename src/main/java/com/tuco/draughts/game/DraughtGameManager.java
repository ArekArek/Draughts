package com.tuco.draughts.game;

import com.tuco.draughts.DraughtsState;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughts.movement.util.Movement;
import lombok.Builder;

import java.util.Optional;

@Builder
public class DraughtGameManager {
    private final DraughtsState state;
    private final MovementMaker playerWhite;
    private final MovementMaker playerBlack;
    private ChangeTurnListener generalChangeTurnListener;
    private ChangeTurnListener whiteChangeTurnListener;
    private ChangeTurnListener blackChangeTurnListener;

    public void makeTurn() {
        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        if (state.isMaximizingTurnNow()) {
            makeTurn(playerWhite, whiteChangeTurnListener);
        } else {
            makeTurn(playerBlack, blackChangeTurnListener);
        }

        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

    private void makeTurn(MovementMaker movementMaker, ChangeTurnListener playerChangeTurnListener) {
        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        Movement movement = movementMaker.takeMove();
        state.makeMove(movement);

        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

}