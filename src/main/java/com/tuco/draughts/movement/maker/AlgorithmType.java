package com.tuco.draughts.movement.maker;

import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.MinMax;
import sac.game.Scout;

public enum AlgorithmType {
    MINMAX, SCOUT, ALPHABETA;

    public GameSearchAlgorithm createAlgorithm() {
        switch (this) {
            case MINMAX:
                return new MinMax();
            case ALPHABETA:
                return new AlphaBetaPruning();
            case SCOUT:
                return new Scout();
            default:
                return new MinMax();
        }
    }
}