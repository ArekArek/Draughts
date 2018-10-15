package com.tuco.draughts.movement.maker;

import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.MinMax;
import sac.game.Scout;

public enum AlgorithmType {
    MINMAX(new MinMax()), SCOUT(new Scout()), ALPHABETA(new AlphaBetaPruning());

    private final GameSearchAlgorithm value;

    AlgorithmType(GameSearchAlgorithm value) {
        this.value = value;
    }

    public GameSearchAlgorithm getValue() {
        return value;
    }
}