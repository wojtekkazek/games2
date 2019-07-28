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
    private boolean turnWhite = true;

    private Tile[][] board = new Tile [width][height];

    private Group tilesGroup = new Group();
    private Group checkersGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tilesGroup, checkersGroup);

        for (int y = 0; y < height; y++) {
            for (int x=0; x < width; x++) {
                Tile tile = new Tile(tileSize, (x+y)%2 == 0, x, y);
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

    private MoveResult tryMove(Checker checker, int newX, int newY) {
        if (board[newX][newY].hasChecker() || (newX + newY) %2 == 0) {
            return new MoveResult(MoveType.none);
        }

        if ((turnWhite && checker.getType() != CheckerType.white) || (!(turnWhite) && checker.getType() == CheckerType.white)) {
            return new MoveResult(MoveType.none);
        }

        int x0 = toBoard(checker.getOldX());
        int y0 = toBoard(checker.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == checker.getType().moveDir) {
            return new MoveResult(MoveType.normal);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == checker.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY -y0) / 2;

            if (board[x1][y1].hasChecker() && board[x1][y1].getChecker().getType() != checker.getType()) {
                return new MoveResult(MoveType.kill, board[x1][y1].getChecker());
            }
        }

        return new MoveResult((MoveType.none));
    }

    private int toBoard(double a) {
        return (int)(a + tileSize / 2) / tileSize;
    }

    private Checker makeChecker(CheckerType type, int x, int y) {
        Checker checker = new Checker(type, x, y);

        checker.setOnMouseReleased(e -> {
            int newX = toBoard(checker.getLayoutX());
            int newY = toBoard(checker.getLayoutY());

            MoveResult result = tryMove(checker, newX, newY);

            int x0 = toBoard(checker.getOldX());
            int y0 = toBoard(checker.getOldY());

            switch (result.getType()) {
                case none:
                    checker.abortMove();
                    break;

                case normal:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);
                    turnWhite = !turnWhite;
                    break;

                case kill:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);

                    Checker killedChecker = result.getChecker();
                    board[toBoard(killedChecker.getOldX())][toBoard(killedChecker.getOldY())].setChecker(null);
                    checkersGroup.getChildren().remove(killedChecker);

                    turnWhite = !turnWhite;
                    break;
            }
        });

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