package com.tuco.draughts.game.heuristic;

import sac.StateFunction;

public enum Heuristic {
    SIMPLE(new DraughtsHeuristic(HeuristicCalculator.createSimple())),
    CASUAL(new DraughtsHeuristic(HeuristicCalculator.createCasual())),
    COMPLEX(new DraughtsHeuristic(HeuristicCalculator.createComplex()));

    private final StateFunction value;

    Heuristic(StateFunction value) {
        this.value = value;
    }

    public StateFunction getValue() {
        return value;
    }
}