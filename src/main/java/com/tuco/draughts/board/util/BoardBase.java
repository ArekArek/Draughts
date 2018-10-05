package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BoardBase {

    protected final int boardSize;

    protected Chequer[][] gameBoard;

    protected BoardBase(BoardCreator boardCreator) {
        gameBoard = boardCreator.createBoard();
        boardSize = boardCreator.getBoardSize();
    }

    protected BoardBase(BoardBase boardBase) {
        this.boardSize = boardBase.boardSize;
        this.gameBoard = boardBase.gameBoard.clone();
    }


    public Chequer getChequer(Coordinate coordinate) {
        if (isOutOfBounds(coordinate)) {
            return Chequer.DISABLED;
        }
        return gameBoard[coordinate.getColumn()][coordinate.getRow()];
    }

    public void setChequer(Coordinate coordinate, Chequer chequer) {
        if (isOutOfBounds(coordinate)) {
            return;
        }
        gameBoard[coordinate.getColumn()][coordinate.getRow()] = chequer;
    }

    private boolean isOutOfBounds(Coordinate coordinate) {
        if (coordinate.getRow() < 0 || coordinate.getRow() >= boardSize) {
            return true;
        } else return coordinate.getColumn() < 0 || coordinate.getColumn() >= boardSize;
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