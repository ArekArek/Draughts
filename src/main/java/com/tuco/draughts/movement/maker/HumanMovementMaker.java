package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HumanMovementMaker implements MovementMaker {

    private final DraughtsState draughtsState;
    private final PositionLoader positionLoader;
    private final HumanMovementInformator humanMovementInformator;

    @Override
    public Movement takeMove() throws MoveStoppedException {
        List<Movement> possibleMoves = draughtsState.generatePossibleMoves().getMovements();
        if (possibleMoves.isEmpty()) {
            humanMovementInformator.wrongPositionChosen();
            return takeMove();
        } else {
            return takeMove(possibleMoves, 0);
        }
    }

    private Movement takeMove(List<Movement> sourcePossibleMoves, final int moveCount) throws MoveStoppedException {
        List<Movement> possibleMoves = sourcePossibleMoves.stream().filter(m -> m.getPower() >= moveCount).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            return sourcePossibleMoves.get(0);
        }

        List<Coordinate> possiblePositions = possibleMoves.stream().map(m -> m.getSteps().get(moveCount)).distinct().collect(Collectors.toList());
        humanMovementInformator.choosePosition(possiblePositions);

        Coordinate coordinate = positionLoader.loadPositionFromUser();

        if (!possiblePositions.contains(coordinate)) {
            humanMovementInformator.wrongPositionChosen();
            return takeMove();
        } else {
            possibleMoves = possibleMoves.stream().filter(m -> m.getSteps().get(moveCount).equals(coordinate)).collect(Collectors.toList());
            return takeMove(possibleMoves, moveCount + 1);
        }
    }

    @Override
    public void stop() {
        positionLoader.stop();
    }
}