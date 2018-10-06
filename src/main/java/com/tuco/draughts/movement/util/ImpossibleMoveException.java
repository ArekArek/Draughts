package com.tuco.draughts.movement.util;

import com.tuco.draughts.board.direction.Direction;
import com.tuco.draughts.board.util.Coordinate;

import java.text.MessageFormat;

public class ImpossibleMoveException extends Exception {
    private static final MessageFormat MESSAGE_FORM = new MessageFormat("Impossible move from {0}, caused by {1}");
    private static final MessageFormat MESSAGE_EXTENDED_FORM = new MessageFormat("Impossible move from {0} by {1}, caused by {2}");

    public ImpossibleMoveException(String message) {
        super(message);
    }

    public ImpossibleMoveException(Coordinate coordinate, String message) {
        super(MESSAGE_FORM.format(new Object[]{coordinate, message}));
    }

    public ImpossibleMoveException(Coordinate coordinate, Direction direction, String message) {
        super(MESSAGE_EXTENDED_FORM.format(new Object[]{coordinate, direction, message}));
    }
}