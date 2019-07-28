package com.kodilla.kodillacourse.Checkers;

public class QueenMoveResult {
    private MoveType type;

    private Queen queen;
    private Checker checker;

    public MoveType getType() {
        return type;
    }

    public Queen getQueen() {
        return queen;
    }

    public Checker getChecker() {
        return checker;
    }

    public QueenMoveResult(MoveType type) {
        this(type, null);
    }


    public QueenMoveResult(MoveType type, Checker checker) {
        this.type = type;
        this.checker = checker;
    }

}
