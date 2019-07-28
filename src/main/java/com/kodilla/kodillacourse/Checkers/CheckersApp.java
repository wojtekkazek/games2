package com.kodilla.kodillacourse.Checkers;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application {

    public static final int tileSize = 50;
    public static final int width = 8;
    public static final int height = 8;

    private Tile[][] board = new Tile [width][height];

    private Group tilesGroup = new Group();
    private Group checkersGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tilesGroup, checkersGroup);

        for (int y=0; y < height; y++) {
            for (int x=0; x < width; x++) {
                Tile tile = new Tile(tileSize,(x+y)%2 == 0, x, y);
                board[x][y] = tile;

                tilesGroup.getChildren().add(tile);

                Checker checker = null;

                if (y <= 2 && (x+y)%2 != 0) {
                    checker = makeChecker(CheckerType.red, x, y);
                }

                if (y >= 5 && (x+y)%2 != 0) {
                    checker = makeChecker(CheckerType.white, x, y);
                }

                if (checker != null) {
                    tile.setChecker(checker);
                    checkersGroup.getChildren().add(checker);
                }
            }
        }

        return root;
    }

    private Checker makeChecker(CheckerType type, int x, int y) {
        Checker checker = new Checker(type, x, y);

        return checker;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setCursor(Cursor.HAND);
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
