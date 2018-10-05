package com.tuco.draughts.board.direction;

import com.tuco.draughts.board.util.Coordinate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class Direction {
    private HorizontalDirection horizontalDirection;
    private VerticalDirection verticalDirection;

    private Direction(HorizontalDirection horizontalDirection, VerticalDirection verticalDirection) {
        this.horizontalDirection = horizontalDirection;
        this.verticalDirection = verticalDirection;
    }

    public Direction(Coordinate startCoordinate, Coordinate targetCoordinate) {
        resolveHorizontalDirection(startCoordinate, targetCoordinate);
        resolveVerticalDirection(startCoordinate, targetCoordinate);
    }

    private void resolveHorizontalDirection(Coordinate startCoordinate, Coordinate targetCoordinate) {
        if (targetCoordinate.getColumn() > startCoordinate.getColumn()) {
            this.horizontalDirection = HorizontalDirection.RIGHT;
        } else {
            this.horizontalDirection = HorizontalDirection.LEFT;
        }
    }

    private void resolveVerticalDirection(Coordinate startCoordinate, Coordinate targetCoordinate) {
        if (targetCoordinate.getRow() > startCoordinate.getRow()) {
            this.verticalDirection = VerticalDirection.UP;
        } else {
            this.verticalDirection = VerticalDirection.DOWN;
        }
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