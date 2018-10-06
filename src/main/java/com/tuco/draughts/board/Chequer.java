package com.tuco.draughts.board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Chequer {
    WHITE(1), WHITE_KING(2), BLACK(-1), BLACK_KING(-2), EMPTY(0), DISABLED(0);

    private final int value;

    private static final Set<Chequer> WHITE_SET = new HashSet<>(Arrays.asList(Chequer.WHITE, Chequer.WHITE_KING));
    private static final Set<Chequer> BLACK_SET = new HashSet<>(Arrays.asList(Chequer.BLACK, Chequer.BLACK_KING));
    private static final Set<Chequer> KING_SET = new HashSet<>(Arrays.asList(Chequer.WHITE_KING, Chequer.BLACK_KING));

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
        return (isWhiteTurn && BLACK_SET.contains(this)) || (!isWhiteTurn && WHITE_SET.contains(this));
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