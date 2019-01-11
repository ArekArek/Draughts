package com.tuco.draughts.movement.maker;

import lombok.Getter;
import sac.game.GameSearchAlgorithm;

import java.util.List;

@Getter
public class AIMovementDescription {
    private String bestMove;
    private List<String> bestMoves;
    private double score;
    private int closedCount;
    private double depthReached;
    private long duration;

    public void setValues(GameSearchAlgorithm algorithm, String bestMove) {
        this.bestMove = bestMove;
        this.bestMoves = algorithm.getBestMoves();
        this.score = algorithm.getMovesScores().get(bestMove);
        this.closedCount = algorithm.getClosedStatesCount();
        this.depthReached = algorithm.getDepthReached();
        this.duration = algorithm.getDurationTime();
    }
}