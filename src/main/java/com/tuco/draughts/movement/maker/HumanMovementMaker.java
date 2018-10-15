package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HumanMovementMaker implements MovementMaker {

    private final static Logger LOG = LogManager.getLogger(HumanMovementMaker.class);

    private final DraughtsState draughtsState;
    private final PositionLoader positionLoader;

    @Override
    public Movement takeMove() {
        List<Movement> possibleMoves = draughtsState.generatePossibleMoves().getMovements();

        LOG.log(Level.TRACE, "Insert start position");
        Coordinate startPosition = positionLoader.loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getFirstStep().equals(startPosition)).collect(Collectors.toList());
        if (possibleMoves.isEmpty()) {
            LOG.log(Level.TRACE, "Incorrect start position");
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
                LOG.log(Level.TRACE, "Incorrect move");
            }
        }

        LOG.log(Level.TRACE, "Insert next position");
        Coordinate coordinate = positionLoader.loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getSteps().get(moveCount).equals(coordinate)).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            LOG.log(Level.TRACE, "Incorrect target position");
            return takeMove();
        } else {
            return takeMove(possibleMoves, moveCount + 1);
        }
    }
}