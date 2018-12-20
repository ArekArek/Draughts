package com.tuco.draughts.game.util;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class GameResultHelper {

    private static final int QUEUE_SIZE = 9;
    private static final int FULL_TURN_STATES_COUNT = 4;
    private static final int STAGNANCY_TRESHOLD = 20;

    private LinkedList<DraughtsState> lastStates;

    @Getter
    private Player winner;

    private int kingOrHitMovementCounter = 0;

    public GameResultHelper(DraughtsState state) {
        lastStates = new LinkedList<>();
        saveState(state, null);
    }

    public GameResultHelper(GameResultHelper gameHelper) {
        if (gameHelper != null) {
            this.kingOrHitMovementCounter = gameHelper.kingOrHitMovementCounter;
            this.lastStates = (LinkedList<DraughtsState>) gameHelper.lastStates.clone();
        }
    }

    public void saveState(DraughtsState state, Movement movement) {
        lastStates.add(new DraughtsState(state));
        lastStates.removeIf(b -> lastStates.size() > QUEUE_SIZE);
        if (isKingOrHitMovement(state, movement)) {
            kingOrHitMovementCounter++;
        } else {
            kingOrHitMovementCounter = 0;
        }
    }

    private boolean isKingOrHitMovement(DraughtsState state, Movement movement) {
        if (movement != null) {
            if (movement.getHits().isEmpty()) {
                return true;
            }
            Chequer lastMovedChequer = state.getBoard().getChequer(movement.getLastStep());
            return lastMovedChequer.isKing();
        }
        return false;
    }

    public boolean isGameOver() {
        return isWon() || isDraw();
    }

    private boolean isWon() {
        DraughtsState currentState = lastStates.getLast();
        Player currentPlayer = currentState.getPlayer();
        List<Movement> possibleMoves = currentState.generatePossibleMoves(currentPlayer).getMovements();
        if (possibleMoves.isEmpty()) {
            winner = currentPlayer.getOpponent();
            return true;
        }
        return false;
    }

    private boolean isDraw() {
        if (isStagnancy() || isSameState3thTime()) {
            winner = Player.BOTH;
            return true;
        }
        return false;
    }

    private boolean isStagnancy() {
        return kingOrHitMovementCounter >= STAGNANCY_TRESHOLD;
    }

    private boolean isSameState3thTime() {
        int decrementedSize = lastStates.size() - 1;
        return decrementedSize >= 8
                && lastStates.get(decrementedSize).equals(lastStates.get(decrementedSize - FULL_TURN_STATES_COUNT))
                && lastStates.get(decrementedSize).equals(lastStates.get(decrementedSize - FULL_TURN_STATES_COUNT * 2));
    }
}