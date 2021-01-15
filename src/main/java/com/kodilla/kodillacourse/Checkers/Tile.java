package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.lang.Nullable;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

public class Tile extends Rectangle {

    private int size;
    private Color color;
    private int tileX;
    private int tileY;
    private Checker checker;

    public Tile(int size, boolean white, int tileX, int tileY) {
        this.size = size;
        this.tileX = tileX;
        this.tileY = tileY;

        setWidth(size);
        setHeight(size);

        relocate(tileX * size, tileY * size);

        if(white == true) {
            color = WHITE;
        } else {
            color = GREEN;
        }

        setFill(color);
    }

    public boolean hasChecker() {
        return checker != null;
    }

    public Checker getChecker() {
        if (hasChecker()) {
            return checker;
        } else {
            return null;
        }
    }

    public void deleteChecker() {
        checker = null;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public Color getColor() {
        return color;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileX=" + tileX +
                ", tileY=" + tileY +
                ", checker=" + checker +
                '}';
    }
}