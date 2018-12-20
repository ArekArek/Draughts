package com.tuco.draughts.game.heuristic;

import sac.StateFunction;

public enum Heuristic {
    SIMPLE(new DraughtsHeuristic(HeuristicCalculator.createSimple(HeuristicCalculator.builder().build()))),
    CASUAL(new DraughtsHeuristic(HeuristicCalculator.createCasual(HeuristicCalculator.builder().build()))),
    COMPLEX(new DraughtsHeuristic(HeuristicCalculator.createComplex(HeuristicCalculator.builder().build())));

    private final StateFunction value;

    Heuristic(StateFunction value) {
        this.value = value;
    }

    public StateFunction getValue() {
        return value;
    }
}