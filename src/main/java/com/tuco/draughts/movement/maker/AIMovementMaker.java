package com.tuco.draughts.movement.maker;

import com.tuco.draughts.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementCoder;
import lombok.AllArgsConstructor;
import sac.StateFunction;
import sac.game.GameSearchAlgorithm;

@AllArgsConstructor
public class AIMovementMaker implements MovementMaker {

    private DraughtsState draughtsState;
    private GameSearchAlgorithm algorithm;
    private StateFunction heuristic;

    @Override
    public Movement takeMove() {
        DraughtsState.setHFunction(heuristic);

        algorithm.setInitial(draughtsState);
        algorithm.execute();
        String bestMove = algorithm.getFirstBestMove();
        return MovementCoder.decode(bestMove);
    }
}