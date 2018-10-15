package com.tuco.draughts.game;

import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughts.movement.util.Movement;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
public class DraughtGameManager {
    @Getter
    private final DraughtsState state;
    private final MovementMaker playerWhite;
    private final MovementMaker playerBlack;
    private ChangeTurnListener generalChangeTurnListener;
    private ChangeTurnListener whiteChangeTurnListener;
    private ChangeTurnListener blackChangeTurnListener;

    @Getter
    private boolean playing = false;

    public void play() {
        playing = true;
        while (!state.isTerminal()) {
            makeTurn();
        }
        playing = false;
    }

    private void makeTurn() {
        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        if (state.isMaximizingTurnNow()) {
            makeDetailedTurn(playerWhite, whiteChangeTurnListener);
        } else {
            makeDetailedTurn(playerBlack, blackChangeTurnListener);
        }

        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

    private void makeDetailedTurn(MovementMaker movementMaker, ChangeTurnListener playerChangeTurnListener) {
        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        Movement movement = movementMaker.takeMove();
        state.makeMove(movement);

        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::afterTurn);
    }

}