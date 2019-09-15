package com.kodilla.kodillacourse.Checkers;

public enum MoveType {
    NONE("NONE"), NORMAL("NORMAL"), KILL("KILL");

    final String levelName;

    MoveType(String levelName) {
        this.levelName = levelName;
    }
}

