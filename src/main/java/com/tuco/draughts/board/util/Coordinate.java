package com.tuco.draughts.board.util;

import com.tuco.draughts.board.direction.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Coordinate {
    @NonNull
    private int column;
    @NonNull
    private int row;

    public Coordinate() {
        this.column = -1;
        this.row = -1;
    }

    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Coordinate(Coordinate coordinate) {
        this.column = this.getColumn();
        this.row = this.getRow();
    }

    public Coordinate plus(Direction direction) {
        this.column += direction.getHorizontalDirection().getValue();
        this.row += direction.getVerticalDirection().getValue();
        return this;
    }
}