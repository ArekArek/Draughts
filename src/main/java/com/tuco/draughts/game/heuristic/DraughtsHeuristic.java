package com.tuco.draughts.game.heuristic;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
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
            double wholeGrade = calculateWholeGrade(draughtsState.getBoard());
            return (wholeGrade > 0) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        } else {
            return calculateWholeGrade(draughtsState.getBoard());
        }
    }

    private double calculateWholeGrade(Board board) {
        List<Coordinate> whiteCoordinates = board.getPlayerCoordinates(true);
        int whiteSum = calculator.calculateValue(board, whiteCoordinates);

        List<Coordinate> blackCoordinates = board.getPlayerCoordinates(false);
        int blackSum = calculator.calculateValue(board, blackCoordinates);

        return whiteSum - blackSum;
    }
}