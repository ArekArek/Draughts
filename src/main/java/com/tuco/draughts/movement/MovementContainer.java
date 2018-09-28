package com.tuco.draughts.movement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MovementContainer {
    private List<Movement> movements;

    public MovementContainer() {
        movements = new ArrayList<>();
    }

    public MovementContainer(List<Movement> movements) {
        this.movements = movements;
    }

    public void insertMovements(MovementContainer prospectiveMoves) {
        if (prospectiveMoves.getPower() > this.getPower()) {
            setMovements(prospectiveMoves);
        } else if (prospectiveMoves.getPower() == this.getPower()) {
            addMovements(prospectiveMoves);
        }
    }

    private void setMovements(MovementContainer movementContainer) {
        this.movements = new ArrayList<>(movementContainer.movements);
    }

    private void addMovements(MovementContainer movementContainer) {
        this.movements.addAll(movementContainer.movements);
    }

    public void insertMovement(Movement movement) {
        if (movement.getPower() > this.getPower()) {
            setMovement(movement);
        } else if (movement.getPower() == this.getPower()) {
            addMovement(movement);
        }
    }

    private void setMovement(Movement movement) {
        this.movements = new ArrayList<>();
        addMovement(movement);
    }

    private void addMovement(Movement movement) {
        movements.add(movement);
    }

    public int getPower() {
        Optional<Movement> movement = movements.stream()
                .max(Comparator.comparing(Movement::getPower));
        return movement.map(Movement::getPower).orElse(0);
    }
}