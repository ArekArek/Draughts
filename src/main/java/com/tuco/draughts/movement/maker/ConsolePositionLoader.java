package com.tuco.draughts.movement.maker;

import com.tuco.draughts.board.util.Coordinate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ConsolePositionLoader implements PositionLoader {

    private final static Logger LOG = LogManager.getLogger(ConsolePositionLoader.class);

    @Override
    public Coordinate loadPositionFromUser() {
        Scanner scanner = new Scanner(System.in);

        LOG.log(Level.TRACE, "\tInsert column number: ");
        int column = scanner.nextInt();
        LOG.log(Level.TRACE, "\tInsert row number: ");
        int row = scanner.nextInt();

        return new Coordinate(column, row);
    }
}