package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.movement.util.Movement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;

@EqualsAndHashCode
public class BoardBase {

    protected final int boardSize;

    protected final Chequer[][] gameBoard;

    @Getter
    @EqualsAndHashCode.Exclude
    private final BoardBaseUtil boardUtil;

    protected BoardBase(BoardCreator boardCreator) {
        gameBoard = boardCreator.createBoard();
        boardSize = boardCreator.getBoardSize();
        boardUtil = new BoardBaseUtil(this);
    }

    protected BoardBase(BoardBase boardBase) {
        boardSize = boardBase.boardSize;
        gameBoard = cloneBoard(boardBase.gameBoard);
        boardUtil = new BoardBaseUtil(this);
    }

    private static Chequer[][] cloneBoard(Chequer[][] source) {
        return Arrays.stream(source).map(Chequer[]::clone).toArray(Chequer[][]::new);
    }

    public Chequer getChequer(Coordinate coordinate) {
        if (boardUtil.isOutOfBounds(coordinate)) {
            return Chequer.DISABLED;
        }
        return gameBoard[coordinate.getColumn()][coordinate.getRow()];
    }

    private void setChequer(Coordinate coordinate, Chequer chequer) {
        if (boardUtil.isOutOfBounds(coordinate)) {
            return;
        }
        gameBoard[coordinate.getColumn()][coordinate.getRow()] = chequer;
    }

    private void clearChequer(Coordinate coordinate) {
        if (!boardUtil.isOutOfBounds(coordinate)) {
            gameBoard[coordinate.getColumn()][coordinate.getRow()] = Chequer.EMPTY;
        }
    }

    public void executeMove(Movement movement) {
        Coordinate startCoordinate = movement.getFirstStep();
        Chequer startChequer = getChequer(startCoordinate);
        Coordinate finalCoordinate = movement.getLastStep();

        movement.getSteps().forEach(this::clearChequer);
        movement.getHits().forEach(this::clearChequer);
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