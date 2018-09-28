package com.tuco.draughts.movement;

import com.tuco.draughts.board.util.Coordinate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Movement {

    private List<Coordinate> steps;

    @Getter
    private boolean finished;

    public Movement(Coordinate startPosition) {
        steps = new ArrayList<>();
        addStep(startPosition);
    }

    public Movement(Movement parent) {
        this.steps = new ArrayList<>(parent.steps);
        this.finished = parent.finished;
    }

    public Movement addStep(Coordinate coordinate) {
        steps.add(coordinate);
        return this;
    }

    public Coordinate getLastStep() {
        return steps.get(steps.size() - 1);
    }

    public boolean wasVisited(Coordinate coordinate) {
        return steps.contains(coordinate);
    }

    public Movement finish() {
        finished = true;
        return this;
    }

    public int getPower() {
        return steps.size() - 1;
    }
}