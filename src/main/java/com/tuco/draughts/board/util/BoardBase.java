package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Place;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class BoardBase {

    protected final int boardSize;

    protected Place[][] gameBoard;

    public BoardBase(BoardCreator boardCreator) {
        gameBoard = boardCreator.createBoard();
        boardSize = boardCreator.getBoardSize();
    }

    public BoardBase(BoardBase boardBase) {
        boardSize = boardBase.boardSize;
        createClonedBoard(boardBase);
    }

    private void createClonedBoard(BoardBase boardBase) {
        for (int i = 0; i < boardBase.gameBoard.length; i++) {
            System.arraycopy(boardBase.gameBoard[i], 0, gameBoard[i], 0, boardBase.gameBoard[i].length);
        }
    }

    public Place getPlace(Coordinates coordinates) {
        return getPlace(coordinates.getColumn(), coordinates.getRow());
    }

    public Place getPlace(int column, int row) {
        return gameBoard[column][row];
    }

    public void setPlace(Coordinates coordinates, Place place) {
        setPlace(coordinates.getColumn(), coordinates.getRow(), place);
    }

    public void setPlace(int x, int y, Place place) {
        gameBoard[x][y] = place;
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