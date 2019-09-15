package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.kodillacourse.Checkers.CheckersApp.tileSize;


public class Checker extends StackPane {

    private CheckerType type;
    private boolean isQueen;
    private double mouseX;
    private double mouseY;
    private double oldX;
    private double oldY;

    public CheckerType getType() {
        return type;
    }

    public boolean getIfIsQueen() {
        return isQueen;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Checker(CheckerType type, boolean isQueen, int x, int y) {
        this.type = type;
        this.isQueen = isQueen;

        move(x, y);

        Circle circle = new Circle(tileSize *0.3);
        circle.setFill(type.getCheckerColor());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(tileSize * 0.03);

        circle.setTranslateX((tileSize - tileSize * 0.3 *2)/2);
        circle.setTranslateY((tileSize - tileSize * 0.3 *2)/2);

        getChildren().addAll(circle);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * tileSize;
        oldY = y * tileSize;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }

    public void transformToQueen() {
        isQueen = true;
        Circle crown = new Circle(tileSize *0.2);
        crown.setFill(Color.BLACK);
        crown.setTranslateX((tileSize - tileSize * 0.2 * 3)/2);
        crown.setTranslateY((tileSize - tileSize * 0.2 * 3)/2);
        getChildren().addAll(crown);
    }

    @Override
    public String toString() {
        return "Checker{" +
                "oldX=" + oldX +
                ", oldY=" + oldY +
                '}';
    }
}
