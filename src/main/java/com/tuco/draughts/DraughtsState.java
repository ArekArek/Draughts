package com.tuco.draughts;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.BoardCreator;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsDefaultHeuristic;
import com.tuco.draughts.movement.MovementHelper;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughts.movement.util.MovementCoder;
import com.tuco.draughts.movement.util.MovementContainer;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.ArrayList;
import java.util.List;

public class DraughtsState extends GameStateImpl {

    private final static Logger LOG = LogManager.getLogger(DraughtsState.class);

    @Getter
    private final Board board;

    private final MovementHelper movementHelper;

    static {
        setHFunction(new DraughtsDefaultHeuristic());
    }

    public DraughtsState(BoardCreator boardCreator) {
        this.board = new Board(boardCreator);
        this.movementHelper = new MovementHelper(board);
        setMaximizingTurnNow(true);
    }

    private DraughtsState(DraughtsState parent) {
        this.board = new Board(parent.board);
        this.movementHelper = new MovementHelper(board);
        setMaximizingTurnNow(parent.maximizingTurnNow);
    }

    private void endTurn() {
        board.updateKings();
        setMaximizingTurnNow(!maximizingTurnNow);
    }

    public MovementContainer generatePossibleMoves() {
        return movementHelper.generatePossibleMoves(isMaximizingTurnNow());
    }

    public boolean isTerminal() {
        return board.countWhiteCheckers() == 0 || board.countBlackCheckers() == 0;
    }

    public void makeMove(Movement movement) {
        board.executeMove(movement);
        endTurn();
    }

    @Override
    public List<GameState> generateChildren() {
        List<GameState> children = new ArrayList<>();
        MovementContainer possibleMoves = generatePossibleMoves();
        possibleMoves.getMovements().forEach(movement -> children.add(generateChild(movement)));
        return children;
    }

    private GameState generateChild(Movement movement) {
        DraughtsState child = new DraughtsState(this);
        child.makeMove(movement);
        child.setMoveName(MovementCoder.code(movement));
        return child;
    }

    @Override
    public String toString() {
        return "Checkers board:\n" + board.toString();
    }
}