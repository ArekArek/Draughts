package com.tuco.draughts.game.heuristic;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class HeuristicCalculator {

    private static final double PAWN_VALUE = 1;
    private static final double KING_VALUE = 2;
    private static final double SAFE_FACTOR = 0.05;
    private static final double DISTANCE_FACTOR = -0.02;
    private static final double FIRST_LINE_VALUE = 0.05;
    private static final double DEFENDER_KING_VALUE = 0.05;
    private static final double KING_MAIN_DIAGONAL = 0.05;
    private static final double TRIANGLE_VALUE = 0.05;
    private static final double OREO_VALUE = 0.04;
    private static final double BRIDGE_VALUE = 0.03;
    private static final double DOG_VALUE = 0.03;

    private final List<BiFunction<Board, Coordinate, Double>> multipleMethods = new ArrayList<>();
    private final List<BiFunction<Board, Boolean, Double>> singleMethods = new ArrayList<>();

    public double calculateValue(Board board, List<Coordinate> coordinates) {
        double multipleValues = coordinates.stream()
                .mapToDouble(coord -> multipleMethods.stream()
                        .mapToDouble(method -> method.apply(board, coord))
                        .sum())
                .sum();

        double singleValues = singleMethods.stream()
                .mapToDouble(method -> method.apply(board, board.getChequer(coordinates.get(0)).isWhite()))
                .sum();

        return multipleValues + singleValues;
    }

    public static HeuristicCalculator createSimple() {
        return new HeuristicCalculator().actType();
    }

    public static HeuristicCalculator createCasual() {
        return new HeuristicCalculator()
                .actType()
                .actSafe()
                .actDistance()
                .actFirstLine();
    }

    public static HeuristicCalculator createComplex() {
        return new HeuristicCalculator()
                .actType()
                .actSafe()
                .actDistance()
                .actFirstLine()
                .actKingDefender()
                .actKingDiagonal()
                .actTrianglePattern()
                .actOreoPattern()
                .actBridgePattern()
                .actDogPattern();
    }

    public HeuristicCalculator actType() {
        multipleMethods.add((b, c) -> b.getChequer(c).isKing() ? KING_VALUE : PAWN_VALUE);
        return this;
    }

    public HeuristicCalculator actSafe() {
        multipleMethods.add((b, c) -> {
            if (b.getBoardUtil().isOnBounds(c)) {
                return SAFE_FACTOR * (b.getChequer(c).isKing() ? KING_VALUE : PAWN_VALUE);
            } else {
                return 0.0;
            }
        });
        return this;
    }

    public HeuristicCalculator actDistance() {
        multipleMethods.add((b, c) -> {
            if (b.getChequer(c).isKing()) {
                return 0.0;
            } else {
                return b.getBoardUtil().getDistanceToPromotion(c) * DISTANCE_FACTOR;
            }
        });
        return this;
    }

    public HeuristicCalculator actFirstLine() {
        multipleMethods.add((b, c) -> {
            if (!b.getChequer(c).isKing() && b.getBoardUtil().isOnFirstLine(c)) {
                return FIRST_LINE_VALUE;
            } else {
                return 0.0;
            }
        });
        return this;
    }

    public HeuristicCalculator actKingDefender() {
        multipleMethods.add((b, c) -> {
            if (b.getChequer(c).isKing() && b.getBoardUtil().isDefender(c)) {
                return DEFENDER_KING_VALUE;
            } else {
                return 0.0;
            }
        });
        return this;
    }

    public HeuristicCalculator actKingDiagonal() {
        multipleMethods.add((b, c) -> {
            if (b.getChequer(c).isKing() && c.getRow() == c.getColumn()) {
                return KING_MAIN_DIAGONAL;
            } else {
                return 0.0;
            }
        });
        return this;
    }

    public HeuristicCalculator actTrianglePattern() {
        singleMethods.add((b, c) -> b.getBoardUtil().isTrianglePatter(c) ? TRIANGLE_VALUE : 0);
        return this;
    }

    public HeuristicCalculator actOreoPattern() {
        singleMethods.add((b, c) -> b.getBoardUtil().isOreoPatter(c) ? OREO_VALUE : 0);
        return this;
    }

    public HeuristicCalculator actBridgePattern() {
        singleMethods.add((b, c) -> b.getBoardUtil().isBridgePatter(c) ? BRIDGE_VALUE : 0);
        return this;
    }

    public HeuristicCalculator actDogPattern() {
        singleMethods.add((b, c) -> b.getBoardUtil().isDogPatter(c) ? DOG_VALUE : 0);
        return this;
    }

}