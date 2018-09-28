package com.tuco.draughts;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.BoardCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.List;

public class DraughtsState extends GameStateImpl {

    private final static Logger LOG = LogManager.getLogger(DraughtsState.class);

    private final Board board;

    static {
//        TODO: set heuristic
//        setHFunction(new MyHeuristic());
    }

    public DraughtsState(BoardCreator boardCreator) {
        this.board = new Board(boardCreator);
        setMaximizingTurnNow(true);
    }

    public DraughtsState(DraughtsState parent) {
        this.board = new Board(parent.board);
        setMaximizingTurnNow(!parent.maximizingTurnNow);
    }

    public void endTurn() {
        board.updateKings();
        setMaximizingTurnNow(!maximizingTurnNow);
    }

    public boolean isTerminal() {
        return board.countWhiteCheckers() == 0 || board.countBlackCheckers() == 0;
    }

    @Override
    public List<GameState> generateChildren() {
//        TODO:
        return null;
    }

    @Override
    public String toString() {
        return "Checkers board:\n" + board.toString();
    }
}