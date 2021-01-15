package com.kodilla.kodillacourse.Checkers;

import java.util.Objects;

public class Move {

    private MoveType moveType;
    private Checker checker;
    private Tile newTile;

    public Move(MoveType moveType, Checker checker, Tile newTile) {
        this.moveType = moveType;
        this.checker = checker;
        this.newTile = newTile;
    }

    public Checker getChecker() {
        return checker;
    }

    public Tile getNewTile() {
        return newTile;
    }

    @Override
    public String toString() {
        return "Move{" +
                "moveType=" + moveType +
                ", checker=" + checker +
                ", newTile=" + newTile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return moveType == move.moveType &&
                Objects.equals(checker, move.checker) &&
                Objects.equals(newTile, move.newTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveType, checker, newTile);
    }
}
