package com.tuco.draughts.board;

import com.tuco.draughts.board.util.BoardBase;
import com.tuco.draughts.board.util.BoardCreator;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.board.util.HeuristicBoardUtil;
import com.tuco.draughts.game.util.Player;
import com.tuco.draughts.movement.util.Movement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Board extends BoardBase {

    private final static Logger LOG = LogManager.getLogger(Board.class);

    @Getter
    @EqualsAndHashCode.Exclude
    private final HeuristicBoardUtil heuristicUtil;

    public Board(BoardCreator boardCreator) {
        super(boardCreator);
        heuristicUtil = new HeuristicBoardUtil(this);
    }

    public Board(BoardBase boardBase) {
        super(boardBase);
        heuristicUtil = new HeuristicBoardUtil(this);
    }

    public List<Coordinate> getPlayerCoordinates(Player player) {
        LOG.debug("Getting player coordinates");
        List<Coordinate> playerCoordinates = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (player == Player.WHITE) {
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

    public void executeMove(Movement movement) {
        Coordinate startCoordinate = movement.getFirstStep();
        Chequer startChequer = getChequer(startCoordinate);
        Coordinate finalCoordinate = movement.getLastStep();

        movement.getSteps().forEach(this::clearChequer);
        movement.getHits().forEach(this::clearChequer);
        setChequer(finalCoordinate, startChequer);
    }
}
