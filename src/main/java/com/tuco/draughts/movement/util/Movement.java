package com.tuco.draughts.movement.util;

import com.tuco.draughts.board.util.Coordinate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Movement {

    @Getter
    private final List<Coordinate> steps;

    @Getter
    private final Set<Coordinate> hits;

    public Movement(Coordinate startPosition) {
        steps = new ArrayList<>();
        hits = new HashSet<>();
        addStep(startPosition);
    }

    public Movement(Movement parent) {
        this.steps = new ArrayList<>(parent.steps);
        this.hits = new HashSet<>(parent.hits);
    }

    public Movement(List<Coordinate> steps, Set<Coordinate> hits) {
        this.steps = steps;
        this.hits = hits;
    }

    public Movement addStep(Coordinate coordinate) {
        steps.add(coordinate);
        return this;
    }

    public Coordinate getFirstStep() {
        return steps.isEmpty() ? new Coordinate() : steps.get(0);
    }

    public Coordinate getLastStep() {
        return steps.get(steps.size() - 1);
    }

    public boolean wasHitted(Coordinate hit) {
        return hits.contains(hit);
    }

    public int getPower() {
        return steps.size() - 1;
    }

    public void addHit(Coordinate hit) {
        hits.add(hit);
    }

    public String toString() {
        return "Movement(steps=" + this.steps.stream().map(Objects::toString).collect(Collectors.joining("\n\t", "\n\t", "\n\t")) + ")";
    }
}