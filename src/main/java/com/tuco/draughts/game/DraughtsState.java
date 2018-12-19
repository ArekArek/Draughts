package com.tuco.draughts.game;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.BoardCreator;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.heuristic.Heuristic;
import com.tuco.draughts.game.util.GameResultHelper;
import com.tuco.draughts.game.util.Player;
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
import java.util.Objects;

public class DraughtsState extends GameStateImpl {

    private final static Logger LOG = LogManager.getLogger(DraughtsState.class);

    @Getter
    private final Board board;

    private final MovementHelper movementHelper;
    private final GameResultHelper resultHelper;

    static {
        setHFunction(Heuristic.SIMPLE.getValue());
    }

    public DraughtsState(BoardCreator boardCreator) {
        this.board = new Board(boardCreator);
        this.movementHelper = new MovementHelper(board);
        this.resultHelper = new GameResultHelper(this);
        setMaximizingTurnNow(true);
    }

    public DraughtsState(DraughtsState parent) {
        this.board = new Board(parent.board);
        this.movementHelper = new MovementHelper(board);
        this.resultHelper = new GameResultHelper(parent.resultHelper);
        setMaximizingTurnNow(parent.maximizingTurnNow);
    }

    public MovementContainer generatePossibleMoves() {
        return generatePossibleMoves(getPlayer());
    }

    public MovementContainer generatePossibleMoves(Player player) {
        return movementHelper.generatePossibleMoves(player);
    }

    public boolean isTerminal() {
        return resultHelper.isGameOver();
    }

    public Player getWinner() {
        return resultHelper.getWinner();
    }

    public void makeMove(Movement movement) {
        board.executeMove(movement);
        board.updateKings();
        swapPlayer();
        resultHelper.saveState(this);
    }

    private void swapPlayer() {
        setMaximizingTurnNow(!maximizingTurnNow);
    }

    public Player getPlayer() {
        return maximizingTurnNow ? Player.WHITE : Player.BLACK;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DraughtsState that = (DraughtsState) o;
        return Objects.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    @Override
    public String toGraphvizLabel() {
        StringBuilder builder = new StringBuilder();
        builder.append("<TABLE BORDER='0' CELLBORDER='1' CELLSPACING='0'> ");
        int k = 0;
        for (int i = 7; i >= 0; i--) {
            builder.append("<TR>");
            for (int j = 0; j < 8; j++) {
                builder.append("<TD>" + board.getChequer(new Coordinate(j, i)).toCharacter() + " </TD>");
                k++;
            }
            builder.append(" </TR>");
        }
        builder.append("</TABLE>");
        return builder.toString();
    }
}