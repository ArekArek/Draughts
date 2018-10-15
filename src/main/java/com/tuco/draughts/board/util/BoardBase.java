package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.movement.util.Movement;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

@EqualsAndHashCode
public class BoardBase {

    protected final int boardSize;

    protected final Chequer[][] gameBoard;

    protected BoardBase(BoardCreator boardCreator) {
        gameBoard = boardCreator.createBoard();
        boardSize = boardCreator.getBoardSize();
    }

    protected BoardBase(BoardBase boardBase) {
        boardSize = boardBase.boardSize;
        gameBoard = cloneBoard(boardBase.gameBoard);
    }

    private static Chequer[][] cloneBoard(Chequer[][] source) {
        return Arrays.stream(source).map(Chequer[]::clone).toArray(Chequer[][]::new);
    }

    public Chequer getChequer(Coordinate coordinate) {
        if (isOutOfBounds(coordinate)) {
            return Chequer.DISABLED;
        }
        return gameBoard[coordinate.getColumn()][coordinate.getRow()];
    }

    private void setChequer(Coordinate coordinate, Chequer chequer) {
        if (isOutOfBounds(coordinate)) {
            return;
        }
        gameBoard[coordinate.getColumn()][coordinate.getRow()] = chequer;
    }

    private void resetChequer(Coordinate coordinate) {
        if (!isOutOfBounds(coordinate)) {
            gameBoard[coordinate.getColumn()][coordinate.getRow()] = Chequer.EMPTY;
        }
    }

    private boolean isOutOfBounds(Coordinate coordinate) {
        if (coordinate.getRow() < 0 || coordinate.getRow() >= boardSize) {
            return true;
        } else return coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
    }

    public void executeMove(Movement movement) {
        Coordinate startCoordinate = movement.getFirstStep();
        Chequer startChequer = getChequer(startCoordinate);
        Coordinate finalCoordinate = movement.getLastStep();

        movement.getSteps().forEach(this::resetChequer);
        movement.getHits().forEach(this::resetChequer);
        setChequer(finalCoordinate, startChequer);
    }

    public Chequer[][] getBase() {
        return cloneBoard(gameBoard);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = boardSize - 1; i >= 0; i--) {
            sb.append(i);
            sb.append(" ");
            for (int j = 0; j < boardSize; j++) {
                sb.append(gameBoard[j][i].toCharacter());
            }
            sb.append("\n");
        }
        sb.append("  ");
        for (int i = 0; i < boardSize; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
}