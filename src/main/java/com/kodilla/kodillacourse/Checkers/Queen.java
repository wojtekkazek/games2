package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.kodillacourse.Checkers.CheckersApp.tileSize;

public class Queen extends StackPane {
    private QueenType type;
    private double mouseX;
    private double mouseY;
    private double oldX;
    private double oldY;

    public QueenType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Queen(QueenType type, int x, int y) {
        this.type = type;

        move(x, y);

        Circle circle = new Circle(tileSize *0.3);
        circle.setFill(type.getQueenColor());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(tileSize * 0.03);

        circle.setTranslateX((tileSize - tileSize * 0.3 *2)/2);
        circle.setTranslateY((tileSize - tileSize * 0.3 *2)/2);

        Circle crown = new Circle(tileSize *0.2);
        crown.setFill(Color.BLACK);
        crown.setTranslateX((tileSize - tileSize * 0.2 * 3)/2);
        crown.setTranslateY((tileSize - tileSize * 0.2 * 3)/2);

        getChildren().addAll(circle, crown);

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
