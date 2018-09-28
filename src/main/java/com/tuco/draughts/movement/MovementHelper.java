package com.tuco.draughts.movement;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.finder.CasualMovementFinder;
import com.tuco.draughts.movement.finder.KingMovementFinder;
import com.tuco.draughts.movement.finder.MovementFinder;

import java.util.List;

public class MovementHelper {

    private final Board board;

    public MovementHelper(Board board) {
        this.board = board;
    }

    public MovementContainer generatePossibleMoves(boolean isWhiteTurn) {
        List<Coordinate> possibleStartCoordinates = board.getPlayerCoordinates(isWhiteTurn);
        MovementContainer result = new MovementContainer();

        for (Coordinate startCoordinate : possibleStartCoordinates) {
            MovementContainer possibleMoves = generatePossibleMoves(startCoordinate, isWhiteTurn);
            result.insertMovements(possibleMoves);
        }

        return result;
    }

    private MovementContainer generatePossibleMoves(Coordinate startCoordinate, boolean isWhiteTurn) {
        Chequer startChequer = board.getChequer(startCoordinate);
        MovementFinder movementFinder = startChequer.isKing() ? new KingMovementFinder() : new CasualMovementFinder(isWhiteTurn, board, startCoordinate);

        return movementFinder.findMoves();
    }
}