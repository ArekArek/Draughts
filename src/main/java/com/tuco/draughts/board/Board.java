package com.tuco.draughts.board;

import com.tuco.draughts.board.util.BoardBase;
import com.tuco.draughts.board.util.BoardCreator;
import com.tuco.draughts.board.util.Coordinates;
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
        long result = Arrays.stream(gameBoard).flatMap(Arrays::stream).filter(Place::isWhite).count();
        LOG.info("Counting white, result: " + result);
        return result;
    }

    public long countBlackCheckers() {
        long result = Arrays.stream(gameBoard).flatMap(Arrays::stream).filter(Place::isBlack).count();
        LOG.info("Counting black, result: " + result);
        return result;
    }

    public List<Coordinates> getWhiteCoordinates(){
        return getPlayerCoordinates(true);
    }

    public List<Coordinates> getBlackCoordinates(){
        return getPlayerCoordinates(false);
    }

    private List<Coordinates> getPlayerCoordinates(boolean isWhiteTurn) {
        LOG.info("Getting player coordinates");
        List<Coordinates> playerCoordinates = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (isWhiteTurn) {
                    if (gameBoard[i][j].isWhite()) {
                        playerCoordinates.add(new Coordinates(i, j));
                    }
                } else if (gameBoard[i][j].isBlack()) {
                    playerCoordinates.add(new Coordinates(i, j));
                }
            }
        }
        return playerCoordinates;
    }

    public void updateKings() {
        for (int i = 0; i < boardSize; i++) {
            if (gameBoard[i][0] == Place.BLACK) {
                gameBoard[i][0] = Place.BLACK_KING;
            } else if (gameBoard[i][boardSize - 1] == Place.WHITE) {
                gameBoard[i][boardSize - 1] = Place.WHITE_KING;
            }
        }
    }
}
