package com.kodilla.kodillacourse.Checkers;

public enum MoveType {
    NONE("NONE"), NORMAL("NORMAL"), KILL("KILL"), QUEENMOVE("MOVEQUEEN"), QUEENKILL("QUEENKILL");

    final String type;

    MoveType(String type) {
        this.type = type;
    }
}

