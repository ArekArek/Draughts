package com.tuco.draughts.movement.finder;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.direction.Direction;
import com.tuco.draughts.board.direction.VerticalDirection;
import com.tuco.draughts.board.util.CaptureCoordinates;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.util.ImpossibleMoveException;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementContainer;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CasualMovementFinder implements MovementFinder {

    private final boolean isWhiteTurn;
    private final Board board;
    private final Coordinate startCoordinate;

    public CasualMovementFinder(boolean isWhiteTurn, Board board, Coordinate startCoordinate) {
        this.isWhiteTurn = isWhiteTurn;
        this.board = board;
        this.startCoordinate = startCoordinate;
    }

    @Override
    public MovementContainer findMoves() {
        MovementContainer result = new MovementContainer();
        result.setCaptured(true);
        boolean thereArePossibleCaptureMoves = processAllDirections(result, new Movement(startCoordinate));
        if (!thereArePossibleCaptureMoves) {
            result.setCaptured(false);
            MovementContainer foundMoves = findNormalGeneralMoves();
            result.insertMovements(foundMoves);
        }
        return result;
    }

    private MovementContainer findNormalGeneralMoves() {
        VerticalDirection verticalDirection = isWhiteTurn ? VerticalDirection.UP : VerticalDirection.DOWN;
        Set<Direction> horizontalDirections = Direction.getAllHorizontalDirections(verticalDirection);
        List<Movement> possibleNormalMoves = horizontalDirections.stream()
                .map(this::findNormalDirectMoves)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new MovementContainer(possibleNormalMoves);
    }

    private Movement findNormalDirectMoves(Direction direction) {
        Coordinate newCoordinate = startCoordinate.plus(direction);
        Chequer newChequer = board.getChequer(newCoordinate);
        if (newChequer.isEmpty()) {
            return new Movement(startCoordinate).addStep(newCoordinate);
        } else {
            return null;
        }
    }

    private boolean findCaptureMove(final MovementContainer movementContainer, final Movement sourceMovement, final Direction direction) {
        try {
            CaptureCoordinates possibleCapture = getPossibleCaptureMove(sourceMovement, direction);
            Movement movement = new Movement(sourceMovement);
            movement.addStep(possibleCapture.getTarget());
            movement.addHit(possibleCapture.getHit());
            boolean isAnyPossible = processAllDirections(movementContainer, movement);

            if (!isAnyPossible) {
                movementContainer.insertMovement(movement);
            }
        } catch (ImpossibleMoveException e) {
            return false;
        }
        return true;
    }

    private CaptureCoordinates getPossibleCaptureMove(Movement movement, Direction direction) throws ImpossibleMoveException {
        Coordinate previousCoordinate = movement.getLastStep();
        Coordinate hitCoordinate = previousCoordinate.plus(direction);

        Chequer hitCoordinateChequer = board.getChequer(hitCoordinate);
        if (hitCoordinateChequer.isEnemy(isWhiteTurn)) {
            Coordinate newCoordinate = hitCoordinate.plus(direction);

            if (movement.wasHitted(hitCoordinate)) {
                throw new ImpossibleMoveException(previousCoordinate, direction, "cannot get back to already visited place");
            }

            Chequer newCoordinateChequer = board.getChequer(newCoordinate);
            if (!newCoordinateChequer.isEmpty()) {
                throw new ImpossibleMoveException(previousCoordinate, direction, "no empty place to finish");
            }

            return new CaptureCoordinates(hitCoordinate, newCoordinate);
        } else {
            throw new ImpossibleMoveException(previousCoordinate, direction, "no enemy to hit");
        }
    }

    private boolean processAllDirections(MovementContainer movementContainer, Movement movement) {
        Set<Direction> allDirections = Direction.getAllDirections();
        long correctMovesCount = allDirections.stream().map(d -> findCaptureMove(movementContainer, movement, d)).filter(t -> t).count();
        return correctMovesCount != 0;
    }
}