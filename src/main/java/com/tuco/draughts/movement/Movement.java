package com.tuco.draughts.movement;

import com.tuco.draughts.board.util.Coordinate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Movement {

    private final List<Coordinate> steps;
    private final Set<Coordinate> hitted;

    @Getter
    private boolean finished;

    public Movement(Coordinate startPosition) {
        steps = new ArrayList<>();
        hitted = new HashSet<>();
        addStep(startPosition);
    }

    public Movement(Movement parent) {
        this.steps = new ArrayList<>(parent.steps);
        this.hitted = new HashSet<>(parent.hitted);
        this.finished = parent.finished;
    }

    public Movement addStep(Coordinate coordinate) {
        steps.add(coordinate);
        return this;
    }

    public Coordinate getLastStep() {
        return steps.get(steps.size() - 1);
    }

    public boolean wasHitted(Coordinate hit) {
        return hitted.contains(hit);
    }

    public Movement finish() {
        finished = true;
        return this;
    }

    public int getPower() {
        return steps.size() - 1;
    }

    public void addHit(Coordinate hit) {
        hitted.add(hit);
    }

    public String toString() {
        return "Movement(steps=" + this.steps.stream().map(Objects::toString).collect(Collectors.joining("\n\t", "\n\t", "\n\t")) + ", finished=" + this.finished + ")";
    }
}