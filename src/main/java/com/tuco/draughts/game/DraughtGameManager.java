package com.tuco.draughts.game;

import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.game.util.Player;
import com.tuco.draughts.movement.maker.AIMovementMaker;
import com.tuco.draughts.movement.maker.MoveStoppedException;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughts.movement.util.Movement;
import lombok.Builder;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Builder
public class DraughtGameManager {
    private final static Logger LOG = LogManager.getLogger(DraughtGameManager.class);

    @Getter
    private final DraughtsState state;
    private final MovementMaker playerWhite;
    private final MovementMaker playerBlack;
    private ChangeTurnListener generalChangeTurnListener;
    private ChangeTurnListener whiteChangeTurnListener;
    private ChangeTurnListener blackChangeTurnListener;

    public void play() {
        while (!state.isTerminal()) {
            try {
                makeTurn();
            } catch (MoveStoppedException e) {
                LOG.info("Move was stopped");
                break;
            }
        }
    }

    private void makeTurn() throws MoveStoppedException {
        Optional.ofNullable(generalChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        Movement movement;
        if (state.getPlayer() == Player.WHITE) {
            movement = makeDetailedTurn(playerWhite, whiteChangeTurnListener);
        } else {
            movement = makeDetailedTurn(playerBlack, blackChangeTurnListener);
        }
        Optional.ofNullable(generalChangeTurnListener).ifPresent(l -> l.afterTurn(movement));
    }

    private Movement makeDetailedTurn(MovementMaker movementMaker, ChangeTurnListener playerChangeTurnListener) throws MoveStoppedException {
        Optional.ofNullable(playerChangeTurnListener).ifPresent(ChangeTurnListener::beforeTurn);

        Movement movement = movementMaker.takeMove();
        state.makeMove(movement);

        if (movementMaker instanceof AIMovementMaker) {
            Optional.ofNullable(generalChangeTurnListener)
                    .ifPresent(l -> l.afterAITurn(((AIMovementMaker) movementMaker).getMovementDescription()));
        }

        Optional.ofNullable(playerChangeTurnListener).ifPresent(l -> l.afterTurn(movement));
        return movement;
    }

    public void stopGame() {
        state.finishGame();
        playerWhite.stop();
        playerBlack.stop();
    }

    public Player getWinner() {
        return state.getWinner();
    }
}