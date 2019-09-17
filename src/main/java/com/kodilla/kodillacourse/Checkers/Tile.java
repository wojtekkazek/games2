package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.*;

public class Tile extends Rectangle {

    private Checker checker;
    private Highlighting highlighting;
    private int tileX;
    private int tileY;
    private int size;

    public boolean hasChecker() {
        return checker != null;
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

    public void setHighlighting(Highlighting highlighting) {
        this.highlighting = highlighting;
    }

    public Tile(int size, boolean white, int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.size = size;

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

    @Override
    public String toString() {
        return "Tile{" +
                "checker=" + checker +
                ", tileX=" + tileX +
                ", tileY=" + tileY +
                '}';
    }
}
