package com.tuco.draughts.game;

import com.tuco.draughts.DraughtsState;
import com.tuco.draughts.movement.Movement;
import com.tuco.draughts.movement.maker.MovementMaker;
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
            makeWhiteTurn(playerWhite, whiteChangeTurnListener);
        } else {
            makeWhiteTurn(playerBlack, blackChangeTurnListener);
        }

        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

    private void makeWhiteTurn(MovementMaker playerWhite, ChangeTurnListener playerChangeTurnListener) {
        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        Movement movement = playerWhite.takeMove();
        state.makeMove(movement);

        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

}