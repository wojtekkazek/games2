package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;

public enum CheckerType {

    RED(1, Color.RED), WHITE(-1, Color.WHITE);

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
