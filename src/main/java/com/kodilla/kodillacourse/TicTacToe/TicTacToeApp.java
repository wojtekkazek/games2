package com.kodilla.kodillacourse.TicTacToe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeApp extends Application {

    public static boolean playable = true;
    public static boolean turnX = true;
    public static Board board = new Board();
    private static BoardDecorator decorator = new BoardDecorator();

    public static void checkState () {

        List<Combo> combos = board.getCombos();

        for(Combo combo : combos) {
            if(combo.isComplete()) {
                playable = false;
                decorator.playWinAnimation(board, combo);
                break;
            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(board.createContent()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
