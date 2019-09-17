package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.kodilla.kodillacourse.Checkers.CheckersApp.tileSize;

public class Highlighting extends StackPane {

    private int x;
    private int y;

    public Highlighting(int x, int y) {
        this.x = x;
        this.y = y;

        relocate(x * tileSize, y * tileSize);

        Rectangle rectangle1 = new Rectangle(tileSize, tileSize*0.2);
        rectangle1.setFill(Color.ORANGE);
        rectangle1.setTranslateX(0);
        rectangle1.setTranslateY(0);

        Rectangle rectangle2 = new Rectangle(tileSize, tileSize*0.2);
        rectangle2.setFill(Color.ORANGE);
        rectangle2.setTranslateX(0);
        rectangle2.setTranslateY(tileSize*0.8);
ad
        /*Rectangle rectangle3 = new Rectangle(tileSize*0.2, tileSize*0.6);
        rectangle3.setFill(Color.ORANGE);
        rectangle3.setTranslateX(0);
        rectangle3.setTranslateY(tileSize*0.2);

        Rectangle rectangle4 = new Rectangle(tileSize*0.2, tileSize*0.6);
        rectangle4.setFill(Color.ORANGE);
        rectangle4.setTranslateX(tileSize*0.8);
        rectangle4.setTranslateY(tileSize*0.2);*/

        getChildren().addAll(rectangle1, rectangle2);
    }
}
