package com.tuco.draughts.board.direction;

public enum VerticalDirection {
    UP(1), DOWN(-1);

    private final int value;

    VerticalDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}