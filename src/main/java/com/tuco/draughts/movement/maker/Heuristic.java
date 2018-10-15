package com.tuco.draughts.movement.maker;

import com.tuco.draughts.game.util.DraughtsSimpleHeuristic;
import sac.StateFunction;

public enum Heuristic {
    SIMPLE(new DraughtsSimpleHeuristic());

    private final StateFunction value;

    Heuristic(StateFunction value) {
        this.value = value;
    }

    public StateFunction getValue() {
        return value;
    }
}