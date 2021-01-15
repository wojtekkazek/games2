package com.kodilla.kodillacourse.TicTacToe;

import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Tile extends StackPane {

    private Text text = new Text();

    public Tile() {
        Rectangle border = new Rectangle(200,200);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(5);

        text.setFont(Font.font(72));

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        setOnMouseClicked(event -> {

            if (!TicTacToeApp.playable)
                return;

            if (getValue() == "X" || getValue() == "O")
                return;

            if (event.getButton() == MouseButton.PRIMARY) {
                if (!TicTacToeApp.turnX)
                    return;
                drawX();
                TicTacToeApp.turnX = false;
                TicTacToeApp.checkState();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (TicTacToeApp.turnX)
                    return;
                drawO();
                TicTacToeApp.turnX = true;
                TicTacToeApp.checkState();
            }
        });
    }

    public double getCentreX() {
        return getTranslateX() + 100;
    }

    public double getCentreY() {
        return getTranslateY() + 100;
    }

    public String getValue() {
        return text.getText();
    }

    private void drawX() {
        text.setText("X");
    }

    private void drawO() {
        text.setText("O");
    }
}
