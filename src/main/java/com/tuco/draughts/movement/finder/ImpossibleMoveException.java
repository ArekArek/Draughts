package com.tuco.draughts.movement.finder;

import com.tuco.draughts.board.direction.Direction;
import com.tuco.draughts.board.util.Coordinate;

import java.text.MessageFormat;

public class ImpossibleMoveException extends Exception {
    private static final MessageFormat MESSAGE_FORM = new MessageFormat("From {0} by {1}, caused by {2}");

    public ImpossibleMoveException(Coordinate coordinate, Direction direction, String message) {
        super(MESSAGE_FORM.format(new Object[]{coordinate, direction, message}));
    }
}