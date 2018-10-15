package com.tuco.draughts.movement.maker;

import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementCoder;
import sac.StateFunction;
import sac.game.GameSearchAlgorithm;
import sac.game.GameSearchConfigurator;

import java.util.List;
import java.util.Random;

public class AIMovementMaker extends GameSearchConfigurator implements MovementMaker {

    private final DraughtsState draughtsState;
    private final StateFunction heuristic;
    private final GameSearchAlgorithm algorithm;
    private static final Random random = new Random();

    public AIMovementMaker(DraughtsState draughtsState, AlgorithmType algorithmType, Heuristic heuristic) {
        this.draughtsState = draughtsState;
        this.heuristic = heuristic.getValue();
        this.algorithm = algorithmType.getValue();

        algorithm.setConfigurator(this);
    }

    @Override
    public Movement takeMove() {
        DraughtsState.setHFunction(heuristic);

        algorithm.setInitial(draughtsState);
        algorithm.execute();

        List<String> bestMoves = algorithm.getBestMoves();
        String bestMove = ((bestMoves.size() == 1) ? bestMoves.get(0) : drawMove(bestMoves));

        return MovementCoder.decode(bestMove);
    }

    private String drawMove(List<String> bestMoves) {
        int index = random.nextInt(bestMoves.size());
        return bestMoves.get(index);
    }
}