package com.tuco.draughts.board.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CaptureCoordinates {
    private Coordinate hit;
    private Coordinate target;
}