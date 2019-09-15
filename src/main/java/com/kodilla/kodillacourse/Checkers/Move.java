package com.kodilla.kodillacourse.Checkers;

public class Move {

    private Checker checker;
    private int newX;
    private int newY;

    public Move(Checker checker, int newX, int newY) {
        this.checker = checker;
        this.newX = newX;
        this.newY = newY;
    }

    public Checker getChecker() {
        return checker;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    @Override
    public String toString() {
        return "Move{" +
                "checker=" + checker +
                ", newX=" + newX +
                ", newY=" + newY +
                '}';
    }
}
