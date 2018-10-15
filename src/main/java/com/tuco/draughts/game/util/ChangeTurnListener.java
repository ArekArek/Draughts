package com.tuco.draughts.game.util;

public interface ChangeTurnListener {

    default void beforeTurn() {
    }

    default void afterTurn() {
    }
}