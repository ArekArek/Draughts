package com.tuco.draughts.board.util;

import com.tuco.draughts.board.direction.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Coordinate {

    private int column;
    private int row;

    public Coordinate() {
        this.column = -1;
        this.row = -1;
    }

    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Coordinate plus(Direction direction) {
        int newColumn = column + direction.getHorizontalDirection().getValue();
        int newRow = row + direction.getVerticalDirection().getValue();
        return new Coordinate(newColumn, newRow);
    }

    @Override
    public String toString() {
        char column = (char) (this.column + 1 + 64);
        char row = (char) (this.row + 1 + 48);
        return "" + column + row;
    }
}