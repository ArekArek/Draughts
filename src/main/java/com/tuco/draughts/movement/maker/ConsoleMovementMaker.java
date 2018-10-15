package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.util.Movement;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConsoleMovementMaker implements MovementMaker {

    final DraughtsState draughtsState;

    @Override
    public Movement takeMove() {
        List<Movement> possibleMoves = draughtsState.generatePossibleMoves().getMovements();

        System.out.println("Insert start position");
        Coordinate startPosition = loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getFirstStep().equals(startPosition)).collect(Collectors.toList());
        if (possibleMoves.isEmpty()) {
            System.out.println("Incorrect start position");
            return takeMove();
        } else {
            return takeMove(possibleMoves, 1);
        }
    }

    private Movement takeMove(List<Movement> sourcePossibleMoves, final int moveCount) {
        List<Movement> possibleMoves = sourcePossibleMoves.stream().filter(m -> m.getPower() >= moveCount).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            if (sourcePossibleMoves.size() == 1) {
                return sourcePossibleMoves.get(0);
            } else {
                System.out.println("Incorrect move");
            }
        }

        System.out.println("Insert next position");
        Coordinate coordinate = loadPositionFromUser();

        possibleMoves = possibleMoves.stream().filter(m -> m.getSteps().get(moveCount).equals(coordinate)).collect(Collectors.toList());

        if (possibleMoves.isEmpty()) {
            System.out.println("Incorrect target position");
            return takeMove();
        } else {
            return takeMove(possibleMoves, moveCount + 1);
        }
    }

    private static Coordinate loadPositionFromUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\tInsert column number: ");
        int column = scanner.nextInt();
        System.out.print("\tInsert row number: ");
        int row = scanner.nextInt();

        return new Coordinate(column, row);
    }
}