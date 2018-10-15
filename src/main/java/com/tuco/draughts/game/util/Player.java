package com.tuco.draughts.game.util;

public enum Player {
    WHITE(true), BLACK(false);

    private boolean value;

    Player(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}