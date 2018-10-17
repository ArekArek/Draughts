package com.tuco.draughts.board;

public enum Chequer {
    WHITE(1), WHITE_KING(2), BLACK(-1), BLACK_KING(-2), EMPTY(0), DISABLED(0);

    private final int value;

    Chequer(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public char toCharacter() {
        switch (this) {
            case DISABLED:
                return ' ';
            case EMPTY:
                return 'O';
            case WHITE:
                return 'W';
            case WHITE_KING:
                return 'w';
            case BLACK:
                return 'B';
            case BLACK_KING:
                return 'b';
            default:
                return ' ';
        }
    }

    public boolean isEnemy(boolean isWhiteTurn) {
        return (isWhiteTurn && isBlack()) || (!isWhiteTurn && isWhite());
    }

    public boolean isKing() {
        return WHITE_KING == this || BLACK_KING == this;
    }

    public boolean isWhite() {
        return WHITE == this || WHITE_KING == this;
    }

    public boolean isBlack() {
        return BLACK == this || BLACK_KING == this;
    }

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }
}