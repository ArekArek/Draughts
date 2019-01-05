package com.tuco.draughts.board.util;

import com.tuco.draughts.board.Chequer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HeuristicBoardUtil {
    private BoardBase board;

    public boolean isOnBounds(Coordinate coordinate) {
        if (coordinate.getRow() == 0 || coordinate.getRow() == board.boardSize - 1) {
            return true;
        } else return coordinate.getColumn() == 0 || coordinate.getColumn() == board.boardSize - 1;
    }

    public int getDistanceToPromotion(Coordinate coordinate) {
        if (board.getChequer(coordinate).isWhite()) {
            return board.boardSize - coordinate.getRow();
        } else {
            return coordinate.getRow();
        }
    }

    public boolean isOnFirstLine(Coordinate coordinate) {
        if (coordinate.getRow() == 0 && board.getChequer(coordinate).isWhite()) {
            return true;
        } else return coordinate.getRow() == (board.boardSize - 1) && board.getChequer(coordinate).isBlack();
    }

    public boolean isDefender(Coordinate coordinate) {
        if (board.getChequer(coordinate).isWhite() && coordinate.getRow() < 2) {
            return true;
        } else return board.getChequer(coordinate).isBlack() && board.boardSize - 2 <= coordinate.getRow();
    }

    public boolean isTrianglePatter(boolean whiteTurn) {
        if (board.boardSize == 8) {
            if (whiteTurn
                    && board.getChequer(new Coordinate(4, 0)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(5, 1)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(6, 0)) == Chequer.WHITE) {
                return true;
            } else return !whiteTurn
                    && board.getChequer(new Coordinate(1, 7)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(2, 6)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(3, 7)) == Chequer.BLACK;
        }
        return false;
    }

    public boolean isOreoPatter(boolean whiteTurn) {
        if (board.boardSize == 8) {
            if (whiteTurn
                    && board.getChequer(new Coordinate(2, 0)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(3, 1)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(4, 0)) == Chequer.WHITE) {
                return true;
            } else return !whiteTurn
                    && board.getChequer(new Coordinate(3, 7)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(4, 6)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(5, 7)) == Chequer.BLACK;
        }
        return false;
    }

    public boolean isBridgePatter(boolean whiteTurn) {
        if (board.boardSize == 8) {
            if (whiteTurn
                    && board.getChequer(new Coordinate(2, 0)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(6, 0)) == Chequer.WHITE) {
                return true;
            } else return !whiteTurn
                    && board.getChequer(new Coordinate(1, 7)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(5, 7)) == Chequer.BLACK;
        }
        return false;
    }

    public boolean isDogPatter(boolean whiteTurn) {
        if (board.boardSize == 8) {
            if (whiteTurn
                    && board.getChequer(new Coordinate(6, 0)) == Chequer.WHITE
                    && board.getChequer(new Coordinate(7, 1)) == Chequer.BLACK) {
                return true;
            } else return !whiteTurn
                    && board.getChequer(new Coordinate(1, 7)) == Chequer.BLACK
                    && board.getChequer(new Coordinate(0, 6)) == Chequer.WHITE;
        }
        return false;
    }
}