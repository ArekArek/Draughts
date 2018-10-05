package com.tuco.draughts.game;

public interface ChangeTurnListener {

    default void beforeTurn() {
    }

    default void afterTurn() {
    }
}