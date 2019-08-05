package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.kodillacourse.Checkers.CheckersApp.tileSize;


public class Checker extends StackPane {

    private CheckerType type;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public CheckerType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Checker(CheckerType type, int x, int y) {
        this.type = type;

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

}
