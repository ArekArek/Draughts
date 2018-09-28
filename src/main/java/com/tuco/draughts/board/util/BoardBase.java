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
        boardSize = boardBase.boardSize;
        createClonedBoard(boardBase);
    }

    private void createClonedBoard(BoardBase boardBase) {
        for (int i = 0; i < boardBase.gameBoard.length; i++) {
            System.arraycopy(boardBase.gameBoard[i], 0, gameBoard[i], 0, boardBase.gameBoard[i].length);
        }
    }

    public Chequer getChequer(Coordinate coordinate) {
        return gameBoard[coordinate.getColumn()][coordinate.getRow()];
    }

    public void setChequer(Coordinate coordinate, Chequer chequer) {
        gameBoard[coordinate.getColumn()][coordinate.getRow()] = chequer;
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