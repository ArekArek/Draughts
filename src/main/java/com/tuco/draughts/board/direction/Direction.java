package com.tuco.draughts.board.direction;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class Direction {
    private final HorizontalDirection horizontalDirection;
    private final VerticalDirection verticalDirection;

    private Direction(HorizontalDirection horizontalDirection, VerticalDirection verticalDirection) {
        this.horizontalDirection = horizontalDirection;
        this.verticalDirection = verticalDirection;
    }

    public static Set<Direction> getAllDirections() {
        Set<Direction> directions = new HashSet<>();
        directions.add(new Direction(HorizontalDirection.RIGHT, VerticalDirection.UP));
        directions.add(new Direction(HorizontalDirection.RIGHT, VerticalDirection.DOWN));
        directions.add(new Direction(HorizontalDirection.LEFT, VerticalDirection.UP));
        directions.add(new Direction(HorizontalDirection.LEFT, VerticalDirection.DOWN));
        return directions;
    }

    public static Set<Direction> getAllHorizontalDirections(VerticalDirection verticalDirection) {
        Set<Direction> directions = new HashSet<>();
        directions.add(new Direction(HorizontalDirection.RIGHT, verticalDirection));
        directions.add(new Direction(HorizontalDirection.LEFT, verticalDirection));
        return directions;
    }

    @Override
    public String toString() {
        return "(" + horizontalDirection + ", " + verticalDirection + ')';
    }
}