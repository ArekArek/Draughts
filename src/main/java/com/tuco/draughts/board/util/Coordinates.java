package com.tuco.draughts.board.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Coordinates {
    @NonNull
    private int column;
    @NonNull
    private int row;

    public Coordinates() {
        this.column = -1;
        this.row = -1;
    }

    public Coordinates(Coordinates coordinates) {
        this.column = this.getColumn();
        this.row = this.getRow();
    }
}