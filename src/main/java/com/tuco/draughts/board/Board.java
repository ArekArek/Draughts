package com.tuco.draughts.board;

import com.tuco.draughts.board.util.BoardBase;
import com.tuco.draughts.board.util.BoardCreator;
import com.tuco.draughts.board.util.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends BoardBase {

    private final static Logger LOG = LogManager.getLogger(Board.class);

    public Board(BoardCreator boardCreator) {
        super(boardCreator);
    }

    public Board(BoardBase boardBase) {
        super(boardBase);
    }

    public long countWhiteCheckers() {
        long result = Arrays.stream(gameBoard).flatMap(Arrays::stream).filter(Chequer::isWhite).count();
        LOG.debug("Counting white, result: " + result);
        return result;
    }

    public long countBlackCheckers() {
        long result = Arrays.stream(gameBoard).flatMap(Arrays::stream).filter(Chequer::isBlack).count();
        LOG.debug("Counting black, result: " + result);
        return result;
    }

    public List<Coordinate> getPlayerCoordinates(boolean isWhiteTurn) {
        LOG.debug("Getting player coordinates");
        List<Coordinate> playerCoordinates = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (isWhiteTurn) {
                    if (gameBoard[i][j].isWhite()) {
                        playerCoordinates.add(new Coordinate(i, j));
                    }
                } else if (gameBoard[i][j].isBlack()) {
                    playerCoordinates.add(new Coordinate(i, j));
                }
            }
        }
        return playerCoordinates;
    }

    public void updateKings() {
        for (int i = 0; i < boardSize; i++) {
            if (gameBoard[i][0] == Chequer.BLACK) {
                gameBoard[i][0] = Chequer.BLACK_KING;
            } else if (gameBoard[i][boardSize - 1] == Chequer.WHITE) {
                gameBoard[i][boardSize - 1] = Chequer.WHITE_KING;
            }
        }
    }
}
