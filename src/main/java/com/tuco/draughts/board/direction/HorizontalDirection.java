package com.tuco.draughts.board.direction;

public enum HorizontalDirection {
    RIGHT(1), LEFT(-1);

    private int value;

    HorizontalDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}