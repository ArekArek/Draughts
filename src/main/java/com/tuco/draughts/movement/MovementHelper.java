package com.tuco.draughts.movement;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.finder.KingMovementFinder;
import com.tuco.draughts.movement.finder.MovementFinder;
import com.tuco.draughts.movement.finder.NormalMovementFinder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MovementHelper {

    private final Board board;

    public MovementContainer generatePossibleMoves(boolean isWhiteTurn) {
        List<Coordinate> possibleStartCoordinates = board.getPlayerCoordinates(isWhiteTurn);
        MovementContainer result = new MovementContainer();

        for (Coordinate startCoordinate : possibleStartCoordinates) {
            MovementContainer possibleMoves = generatePossibleMoves(startCoordinate);
            result.insertMovements(possibleMoves);
        }

        return result;
    }

    private MovementContainer generatePossibleMoves(Coordinate startCoordinate) {
        Chequer startChequer = board.getChequer(startCoordinate);
        MovementFinder movementFinder = startChequer.isKing() ? new KingMovementFinder() : new NormalMovementFinder();

        return movementFinder.findMoves(startCoordinate);
    }
}