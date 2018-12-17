package com.tuco.draughts.game.heuristic;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class HeuristicCalculator {
    private final List<BiFunction<Board, Coordinate, Integer>> methods = new ArrayList<>();

    public int calculateValue(Board board, List<Coordinate> coordinates) {
        return coordinates.stream()
                .mapToInt(coord -> methods.stream()
                        .mapToInt(method -> method.apply(board, coord))
                        .sum())
                .sum();
    }

    public HeuristicCalculator actType() {
        methods.add((b, c) -> b.getChequer(c).isKing() ? 2 : 1);
        return this;
    }
}