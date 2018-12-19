package com.tuco.draughts.game.util;

import com.tuco.draughts.game.DraughtsState;
import lombok.Getter;

import java.util.LinkedList;

public class GameResultHelper {

    private static final int QUEUE_SIZE = 9;
    private static final int FULL_TURN_STATES_COUNT = 4;

    private LinkedList<DraughtsState> lastStates;

    @Getter
    private Player winner;

    public GameResultHelper(DraughtsState state) {
        lastStates = new LinkedList<>();
        saveState(state);
    }

    public GameResultHelper(GameResultHelper gameHelper) {
        if (gameHelper != null) {
            this.lastStates = (LinkedList<DraughtsState>) gameHelper.lastStates.clone();
        }
    }

    public void saveState(DraughtsState state) {
        lastStates.add(new DraughtsState(state));
        lastStates.removeIf(b -> lastStates.size() > QUEUE_SIZE);
    }

    public boolean isGameOver() {
        return isWon() || isDraw();
    }

    private boolean isWon() {
        DraughtsState currentState = lastStates.getLast();
        Player currentPlayer = currentState.getPlayer();
        if (currentState.generatePossibleMoves(currentPlayer == Player.WHITE).getMovements().isEmpty()) {
            winner = currentPlayer.getOpponent();
            return true;
        }
        return false;
    }

    public boolean isDraw() {
        if (isSameState3thTime()) {
            winner = Player.BOTH;
            return true;
        }
        return false;
    }

    private boolean isSameState3thTime() {
        int decrementedSize = lastStates.size() - 1;
        return decrementedSize >= 8
                && lastStates.get(decrementedSize).equals(lastStates.get(decrementedSize - FULL_TURN_STATES_COUNT))
                && lastStates.get(decrementedSize).equals(lastStates.get(decrementedSize - FULL_TURN_STATES_COUNT * 2));
    }
}