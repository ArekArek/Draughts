package com.tuco.draughts.movement.finder;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.direction.Direction;
import com.tuco.draughts.board.util.CaptureCoordinates;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.Movement;
import com.tuco.draughts.movement.MovementContainer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KingMovementFinder implements MovementFinder {

    private final boolean isWhiteTurn;
    private final Board board;
    private final Coordinate startCoordinate;

    public KingMovementFinder(boolean isWhiteTurn, Board board, Coordinate startCoordinate) {
        this.isWhiteTurn = isWhiteTurn;
        this.board = board;
        this.startCoordinate = startCoordinate;
    }

    @Override
    public MovementContainer findMoves() {
        MovementContainer result = new MovementContainer();
        result.setCaptured(true);
        boolean thereArePossibleCaptureMoves = findAllCaptureMoves(result, new Movement(startCoordinate));
        if (!thereArePossibleCaptureMoves) {
            result.setCaptured(false);
            MovementContainer foundMoves = findNormalGeneralMoves();
            result.insertMovements(foundMoves);
        }
        return result;
    }

    private boolean findAllCaptureMoves(MovementContainer movementContainer, Movement movement) {
        Set<Direction> allDirections = Direction.getAllDirections();
        long correctMovesCount = allDirections.stream().map(d -> findCaptureMove(movementContainer, movement, d)).filter(t -> t).count();
        return correctMovesCount != 0;
    }

    private boolean findCaptureMove(final MovementContainer movementContainer, final Movement sourceMovement, final Direction direction) {
        try {
            Set<CaptureCoordinates> possibleCoordinates = getPossibleCaptureMoves(sourceMovement, direction);
            if (possibleCoordinates.isEmpty()) {
                return false;
            }
            for (CaptureCoordinates possibleMove : possibleCoordinates) {
                Movement movement = new Movement(sourceMovement);
                movement.addStep(possibleMove.getTarget());
                movement.addHit(possibleMove.getHit());
                processPossibleMove(movementContainer, movement);
            }
        } catch (ImpossibleMoveException e) {
            return false;
        }
        return true;
    }

    private Set<CaptureCoordinates> getPossibleCaptureMoves(Movement movement, Direction direction) throws ImpossibleMoveException {
        Coordinate previousCoordinate = movement.getLastStep();
        Coordinate hitCoordinate = findHitCoordinate(previousCoordinate, direction);
        Chequer hitCoordinateChequer = board.getChequer(hitCoordinate);

        if (movement.wasHitted(hitCoordinate)) {
            throw new ImpossibleMoveException(previousCoordinate, direction, "no enemy to hit");
        } else if (hitCoordinateChequer.isEnemy(isWhiteTurn)) {
            return findPossibleCaptureTargetPositions(direction, hitCoordinate);
        } else {
            throw new ImpossibleMoveException(previousCoordinate, direction, "no enemy to hit");
        }
    }

    private Coordinate findHitCoordinate(Coordinate previousCoordinate, Direction direction) throws ImpossibleMoveException {
        Coordinate newCoordinate = previousCoordinate.plus(direction);
        Chequer newChequer = board.getChequer(newCoordinate);
        if (newChequer.isEnemy(isWhiteTurn)) {
            return newCoordinate;
        } else if (newChequer.isEmpty()) {
            return findHitCoordinate(newCoordinate, direction);
        } else {
            throw new ImpossibleMoveException("Cannot find move in this direction " + direction);
        }
    }

    private Set<CaptureCoordinates> findPossibleCaptureTargetPositions(Direction direction, Coordinate hitCoordinate) {
        Set<CaptureCoordinates> result = new HashSet<>();
        Coordinate newCoordinate = hitCoordinate.plus(direction);
        Chequer newCoordinateChequer;

        while (true) {
            newCoordinateChequer = board.getChequer(newCoordinate);
            if (!newCoordinateChequer.isEmpty()) {
                return result;
            }

            result.add(new CaptureCoordinates(hitCoordinate, newCoordinate));

            newCoordinate = newCoordinate.plus(direction);
        }
    }

    private void processPossibleMove(MovementContainer movementContainer, Movement sourceMovement) {
        boolean isAnyPossible = findAllCaptureMoves(movementContainer, sourceMovement);

        if (!isAnyPossible) {
            sourceMovement.finish();
            movementContainer.insertMovement(sourceMovement);
        }
    }

    private MovementContainer findNormalGeneralMoves() {
        Set<Direction> allDirections = Direction.getAllDirections();

        List<Movement> possibleNormalMoves = allDirections.stream()
                .map(this::findPossibleNormalTargetPositions)
                .filter(Predicate.not(Set::isEmpty))
                .flatMap(Collection::stream)
                .map(m -> new Movement(startCoordinate).addStep(m))
                .collect(Collectors.toList());
        return new MovementContainer(possibleNormalMoves);
    }

    private Set<Coordinate> findPossibleNormalTargetPositions(Direction direction) {
        Set<Coordinate> result = new HashSet<>();
        Coordinate newCoordinate = startCoordinate.plus(direction);
        Chequer newCoordinateChequer;

        while (true) {
            newCoordinateChequer = board.getChequer(newCoordinate);
            if (!newCoordinateChequer.isEmpty()) {
                return result;
            }

            result.add(newCoordinate);

            newCoordinate = newCoordinate.plus(direction);
        }
    }
}