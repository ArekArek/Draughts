package com.tuco.draughts.game.heuristic;

import sac.StateFunction;

public enum Heuristic {
    SIMPLE(createSimple());

    private final StateFunction value;

    Heuristic(StateFunction value) {
        this.value = value;
    }

    public StateFunction getValue() {
        return value;
    }

    private static DraughtsHeuristic createSimple() {
        HeuristicCalculator heuristicCalculator = new HeuristicCalculator().actType();
        return new DraughtsHeuristic(heuristicCalculator);
    }
}