package com.tuco.draughts.game.heuristic;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.util.Player;
import sac.State;
import sac.StateFunction;

import java.util.List;

public class DraughtsHeuristic extends StateFunction {
    private HeuristicCalculator calculator;

    public DraughtsHeuristic(HeuristicCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public double calculate(State state) {
        DraughtsState draughtsState = (DraughtsState) state;
        if (draughtsState.isTerminal()) {
            Player winner = draughtsState.getWinner();
            switch (winner) {
                case WHITE:
                    return Double.POSITIVE_INFINITY;
                case BLACK:
                    return Double.NEGATIVE_INFINITY;
                case BOTH:
                    return 0.0;
            }
        }
        return calculateWholeGrade(draughtsState.getBoard());
    }

    private double calculateWholeGrade(Board board) {
        double whiteGrade = getPlayerGrade(board, Player.WHITE);
        double blackGrade = getPlayerGrade(board, Player.BLACK);

        return whiteGrade - blackGrade;
    }

    private double getPlayerGrade(Board board, Player player) {
        List<Coordinate> coordinates = board.getPlayerCoordinates(player);
        double grade = 0;
        if (!coordinates.isEmpty()) {
            grade = calculator.calculateValue(board, coordinates);
        }
        return grade;
    }
}