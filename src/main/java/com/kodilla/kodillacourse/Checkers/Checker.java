package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.kodilla.kodillacourse.Checkers.CheckersApp.tileSize;

public class Checker extends StackPane {

    private CheckerType type;

    public CheckerType getType() {
        return type;
    }

    public Checker(CheckerType type, int x, int y) {
        this.type = type;

        relocate(x * tileSize, y * tileSize);

        Circle circle = new Circle(tileSize *0.3);
        circle.setFill(type.getCheckerColor());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(tileSize * 0.03);

        circle.setTranslateX((tileSize - tileSize * 0.3 *2)/2);
        circle.setTranslateY((tileSize - tileSize * 0.3 *2)/2);

        getChildren().addAll(circle);
    }

}

