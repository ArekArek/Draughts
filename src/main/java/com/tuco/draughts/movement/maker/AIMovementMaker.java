package com.tuco.draughts.movement.maker;

import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.heuristic.Heuristic;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementCoder;
import sac.StateFunction;
import sac.game.GameSearchAlgorithm;
import sac.game.GameSearchConfigurator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AIMovementMaker extends GameSearchConfigurator implements MovementMaker {

    private final DraughtsState draughtsState;
    private final StateFunction heuristic;
    private final GameSearchAlgorithm algorithm;
    private static final Random random = new Random();

    public AIMovementMaker(DraughtsState draughtsState, AlgorithmType algorithmType, Heuristic heuristic) {
        this.draughtsState = draughtsState;
        this.heuristic = heuristic.getValue();
        this.algorithm = algorithmType.createAlgorithm();

        algorithm.setConfigurator(this);
        setQuiescenceOn(false);
    }

    @Override
    public Movement takeMove() {
        DraughtsState.setHFunction(heuristic);
        List<String> bestMoves;

        algorithm.setInitial(draughtsState);
        try {
            algorithm.execute();
        } catch (NullPointerException e) {
        } finally {
            bestMoves = algorithm.getBestMoves();
        }

        String bestMove = ((bestMoves.size() == 1) ? bestMoves.get(0) : drawMove(bestMoves));

        return MovementCoder.decode(bestMove);
    }

    private String drawMove(List<String> bestMoves) {
        if (bestMoves == null || bestMoves.size() == 0) {
            bestMoves = draughtsState.generatePossibleMoves()
                    .getMovements()
                    .stream()
                    .map(MovementCoder::code)
                    .collect(Collectors.toList());
        }
        int index = random.nextInt(bestMoves.size());
        return bestMoves.get(index);
    }

    @Override
    public void stop() {
        algorithm.forceStop();
    }
}