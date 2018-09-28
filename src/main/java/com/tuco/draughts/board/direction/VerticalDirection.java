package com.tuco.draughts.board.direction;

public enum VerticalDirection {
    UP(1), DOWN(-1);

    private int value;

    VerticalDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VerticalDirection fromMaximizingTurn(boolean isMaximizingTurnNow) {
        return isMaximizingTurnNow ? UP : DOWN;
    }
}