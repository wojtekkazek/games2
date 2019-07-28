package com.kodilla.kodillacourse.Checkers;

public class MoveResult {

    private MoveType type;

    private Checker checker;

    public MoveType getType() {
        return type;
    }

    public Checker getChecker() {
        return checker;
    }

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Checker checker) {
        this.type = type;
        this.checker = checker;
    }
}
