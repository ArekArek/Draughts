package com.tuco.draughts.movement;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.util.Player;
import com.tuco.draughts.movement.finder.CasualMovementFinder;
import com.tuco.draughts.movement.finder.KingMovementFinder;
import com.tuco.draughts.movement.finder.MovementFinder;
import com.tuco.draughts.movement.util.MovementContainer;

import java.util.List;

public class MovementHelper {

    private final Board board;

    public MovementHelper(Board board) {
        this.board = board;
    }

    public MovementContainer generatePossibleMoves(Player player) {
        List<Coordinate> possibleStartCoordinates = board.getPlayerCoordinates(player);
        MovementContainer result = new MovementContainer();

        for (Coordinate startCoordinate : possibleStartCoordinates) {
            MovementContainer possibleMoves = generatePossibleMoves(startCoordinate, player);
            result.insertMovements(possibleMoves);
        }

        return result;
    }

    private MovementContainer generatePossibleMoves(Coordinate startCoordinate, Player player) {
        Chequer startChequer = board.getChequer(startCoordinate);
        MovementFinder movementFinder = startChequer.isKing() ? new KingMovementFinder(player, board, startCoordinate) : new CasualMovementFinder(player, board, startCoordinate);

        return movementFinder.findMoves();
    }
}