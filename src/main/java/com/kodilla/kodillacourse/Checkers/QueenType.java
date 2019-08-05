package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;

public enum QueenType {

    RED(1, Color.RED), WHITE(-1, Color.WHITE);

    final int moveDir;
    final Color queenColor;

    QueenType(int moveDir, Color checkerColor) {
        this.moveDir = moveDir;
        this.queenColor = checkerColor;
    }

    public Color getQueenColor() {
        return queenColor;
    }
}
