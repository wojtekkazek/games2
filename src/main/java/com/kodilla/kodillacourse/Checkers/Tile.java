package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

public class Tile extends Rectangle {

    private Checker checker;
    private Queen queen;
    private int tileX;
    private int tileY;

    public boolean hasChecker() {
        return checker != null || queen != null;
    }

    public Checker getChecker() {
        return checker;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public void setQueen(Queen queen) {
        this.queen = queen;
    }

    public Tile(int size, boolean white, int tileX, int tileY) {
        setWidth(size);
        setHeight(size);

        relocate(tileX * size, tileY * size);

        Color color;

        if(white == true) {
            color = WHITE;
        } else {
            color = GREEN;
        }

        setFill(color);

    }

}
