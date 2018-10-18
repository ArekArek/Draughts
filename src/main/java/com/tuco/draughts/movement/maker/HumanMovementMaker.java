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
    public Movement takeMove() {
        List<Movement> possibleMoves = draughtsState.generatePossibleMoves().getMovements();
        humanMovementInformator.choosePosition(possibleMoves);

        Coordinate startPosition = positionLoader.loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getFirstStep().equals(startPosition)).collect(Collectors.toList());
        if (possibleMoves.isEmpty()) {
            humanMovementInformator.wrongPositionChosen();
            return takeMove();
        } else {
            return takeMove(possibleMoves, 1);
        }
    }

    private Movement takeMove(List<Movement> sourcePossibleMoves, final int moveCount) {
        List<Movement> possibleMoves = sourcePossibleMoves.stream().filter(m -> m.getPower() >= moveCount).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            if (sourcePossibleMoves.size() == 1) {
                return sourcePossibleMoves.get(0);
            } else {
                humanMovementInformator.wrongPositionChosen();
            }
        }

        humanMovementInformator.choosePosition(possibleMoves);
        Coordinate coordinate = positionLoader.loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getSteps().get(moveCount).equals(coordinate)).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            humanMovementInformator.wrongPositionChosen();
            return takeMove();
        } else {
            return takeMove(possibleMoves, moveCount + 1);
        }
    }
}