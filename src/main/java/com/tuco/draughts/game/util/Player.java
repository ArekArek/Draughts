package com.tuco.draughts.game.util;

public enum Player {
    WHITE("white"), BLACK("black"), BOTH("both");

    private String value;

    Player(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Player getOpponent() {
        switch (this) {
            case WHITE:
                return BLACK;
            case BLACK:
                return WHITE;
            case BOTH:
                return BOTH;
            default:
                return null;
        }
    }
}