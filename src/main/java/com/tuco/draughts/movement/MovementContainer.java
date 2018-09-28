package com.tuco.draughts.movement;

import java.util.ArrayList;
import java.util.List;

public class MovementContainer {
    private List<Movement> movements;

    private int power;

    public MovementContainer() {
        movements = new ArrayList<>();
    }

    public MovementContainer(List<Movement> movements) {
        this.movements = movements;
    }

    public void insertMovements(MovementContainer prospectiveMoves) {
        if (prospectiveMoves.power > this.power) {
            setMovements(prospectiveMoves);
        } else if (prospectiveMoves.power == this.power) {
            addMovements(prospectiveMoves);
        }
    }

    private void setMovements(MovementContainer movementContainer) {
        this.movements = new ArrayList<>(movementContainer.movements);
        this.power = movementContainer.power;
    }

    private void addMovements(MovementContainer movementContainer) {
        this.movements.addAll(movementContainer.movements);
    }

    public MovementContainer insertMovement(Movement movement) {
        if (movement.getPower() > this.power) {
            setMovement(movement);
        } else if (movement.getPower() == this.power) {
            addMovement(movement);
        }
        return this;
    }

    private void setMovement(Movement movement) {
        this.movements = new ArrayList<>();
        addMovement(movement);
    }

    private void addMovement(Movement movement) {
        movements.add(movement);
    }
}