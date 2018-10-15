package com.tuco.draughts.game.util;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
import sac.State;
import sac.StateFunction;

import java.util.List;

public class DraughtsSimpleHeuristic extends StateFunction {

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
        int whiteSum = whiteCoordinates.stream().mapToInt(x -> board.getChequer(x).getValue()).sum();

        List<Coordinate> blackCoordinates = board.getPlayerCoordinates(false);
        int blackSum = blackCoordinates.stream().mapToInt(x -> board.getChequer(x).getValue()).sum();

        return whiteSum + blackSum;
    }
}