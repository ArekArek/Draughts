package com.tuco.draughts.movement.maker;

import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementCoder;
import sac.StateFunction;
import sac.game.GameSearchAlgorithm;
import sac.game.GameSearchConfigurator;

public class AIMovementMaker extends GameSearchConfigurator implements MovementMaker {

    private final DraughtsState draughtsState;
    private final StateFunction heuristic;
    private final GameSearchAlgorithm algorithm;

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
        String bestMove = algorithm.getFirstBestMove();
        return MovementCoder.decode(bestMove);
    }
}