package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;

public enum CheckerType {

    red(1, Color.RED), white(-1, Color.WHITE);

    final int moveDir;
    final Color checkerColor;

    CheckerType(int moveDir, Color checkerColor) {
        this.moveDir = moveDir;
        this.checkerColor = checkerColor;
    }

    public Color getCheckerColor() {
        return checkerColor;
    }
}

