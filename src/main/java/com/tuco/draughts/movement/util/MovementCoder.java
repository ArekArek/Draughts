package com.tuco.draughts.movement.util;

import com.tuco.draughts.board.util.Coordinate;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class MovementCoder {
    private static final String COORDINATES_DELIMITER = ",";
    private static final String STEPS_DELIMITER = "-";
    private static final String HITS_DELIMITER = "-";
    private static final String PARTS_DELIMITER = ":";

    public static String code(Movement movement) {
        var steps = movement.getSteps().stream().map(MovementCoder::codeCoordinate).collect(Collectors.joining(STEPS_DELIMITER));
        var hits = movement.getHits().stream().map(MovementCoder::codeCoordinate).collect(Collectors.joining(HITS_DELIMITER));
        return steps + PARTS_DELIMITER + hits;
    }

    private static String codeCoordinate(Coordinate coordinate) {
        return coordinate.getColumn() + COORDINATES_DELIMITER + coordinate.getRow();
    }

    private static Coordinate decodeCoordinate(String input) {
        var splitted = input.split(COORDINATES_DELIMITER);
        return new Coordinate(Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]));
    }

    public static Movement decode(String input) {
        var codedParts = input.split(PARTS_DELIMITER);

        var codedSteps = codedParts[0].split(STEPS_DELIMITER);
        var steps = Arrays.stream(codedSteps).map(MovementCoder::decodeCoordinate).collect(Collectors.toList());
        if (codedParts.length <= 1) {
            return new Movement(steps, Collections.emptySet());
        }
        var codedHits = codedParts[1].split(HITS_DELIMITER);
        var hits = Arrays.stream(codedHits).map(MovementCoder::decodeCoordinate).collect(Collectors.toSet());

        return new Movement(steps, hits);
    }
}