package com.tuco.draughts.board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Place {
    WHITE(1), WHITE_KING(5), BLACK(-1), BLACK_KING(-5), EMPTY(0), DISABLED(0);

    private final int value;

    private static final Set<Place> WHITE_SET = new HashSet<>(Arrays.asList(Place.WHITE, Place.WHITE_KING));
    private static final Set<Place> BLACK_SET = new HashSet<>(Arrays.asList(Place.BLACK, Place.BLACK_KING));
    private static final Set<Place> KING_SET = new HashSet<>(Arrays.asList(Place.WHITE_KING, Place.BLACK_KING));
    private static final Set<Place> UNAVAILABLE_SET = new HashSet<>(Arrays.asList(Place.DISABLED, Place.EMPTY));

    Place(int value) {
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

    public boolean isEnemy(boolean isMaximizingTurnNow) {
        return isMaximizingTurnNow && BLACK_SET.contains(this) || !isMaximizingTurnNow && WHITE_SET.contains(this);
    }

    public boolean isFriendly(boolean isMaximizingTurnNow) {
        return !isEnemy(isMaximizingTurnNow) && !UNAVAILABLE_SET.contains(this);
    }

    public boolean isKing() {
        return KING_SET.contains(this);
    }

    public boolean isWhite() {
        return WHITE_SET.contains(this);
    }

    public boolean isBlack() {
        return BLACK_SET.contains(this);
    }

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }
}