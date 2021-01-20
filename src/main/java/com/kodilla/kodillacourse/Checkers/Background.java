package com.kodilla.kodillacourse.Checkers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.kodilla.kodillacourse.Checkers.Board.checkersGroup;
import static com.kodilla.kodillacourse.Checkers.Board.tilesGroup;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;
import static com.kodilla.kodillacourse.Checkers.CheckersApp.timeline;
import static com.kodilla.kodillacourse.Checkers.GameStatus.gameOn;
import static com.kodilla.kodillacourse.Checkers.GameStatus.gameOver;
import static com.kodilla.kodillacourse.Checkers.GameStatus.turnWhite;

public class Background {

    private Pane root = new Pane();
    private static Text turn = new Text("Turn: WHITE");
    private static Text whiteCheckersText = new Text("White Checkers/Queens remaining:");;
    private static Text whiteCheckersQty = new Text("12 / 0");
    private static Text redCheckersText = new Text("Red Checkers/Queens remaining:");
    private static Text redCheckersQty = new Text("12 / 0");
    public static Text gamePaused = new Text("");
    public static Board board = new Board();

    private Button startButton = new Button("START");
    private Button newGameButton = new Button("NEW GAME");
    public static Text stopwatch = new Text("00:00");
    private int minutes = 0;
    private int seconds = 0;
    public static Button giveUpButton = new Button("GIVE UP");
    private Text gameOverText = new Text("");
    private Text gameDurationText = new Text("");
    private int noOfGamesWonByWhite = 0;
    private int noOfGamesWonByRed = 0;
    private Text whiteWins = new Text("WHITE wins: " + noOfGamesWonByWhite);
    private Text redWins = new Text("RED wins: " + noOfGamesWonByRed);

    private Button printCheckers = new Button("PRINT CHECKERS");
    private Button printTiles = new Button("PRINT TILES");
    private Button printGroupOfCheckers = new Button("PRINT GROUP OF CHECKERS");
    private Button printGroupOfTiles = new Button("PRINT GROUP OF TILES");
    private Button printPossibleMoves = new Button("PRINT MOVES");

    private Parent createContent() {
        root.setPrefSize(400, 400);
        root.getChildren().addAll(board.getTilesGroup());
        root.getChildren().addAll(board.getCheckersGroup());
        root.getChildren().addAll(board.getTilesToHighlight());
        return root;
    }

    public void setBackground(Stage primaryStage) {
        Pane content = new Pane(createContent());
        primaryStage.setTitle("Checkers");

        gamePaused.setFill(Color.RED);
        gameOverText.setFill(Color.RED);

        HBox smallHBox = new HBox(10, startButton, newGameButton);
        VBox vbox = new VBox(10, smallHBox, stopwatch, turn, whiteCheckersText, whiteCheckersQty, redCheckersText, redCheckersQty, giveUpButton, gameOverText, gameDurationText,
                gamePaused, whiteWins, redWins,
                printCheckers, printTiles, printGroupOfCheckers, printGroupOfTiles, printPossibleMoves);

        HBox bigHBox = new HBox(10, content, vbox);
        Scene scene = new Scene(bigHBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void updateBackground() {
        if (turnWhite) {
            turn.setText("Turn: WHITE");
        } else {
            turn.setText("Turn: RED");
        }
        whiteCheckersQty.setText(noOfWhiteCheckers + " / " + noOfWhiteQueens);
        redCheckersQty.setText(noOfRedCheckers + " / " + noOfRedQueens);
    }

    void runStopwatch (Text text) {
        if(seconds == 60) {
            minutes++;
            seconds = 0;
        }
        stopwatch.setText((((minutes/10) == 0) ? "0" : "") + minutes + ":"
                + (((seconds /10) == 0) ? "0" : "") + seconds++);
    }

    public void activateButtons() {

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!gameOver) {
                    gameOverText.setText("");
                    if(!gameOn) {
                        timeline.play();
                        gameOn = true;
                        analyzeBoard();
                        startButton.setText("PAUSE");
                        gamePaused.setText("");
                    } else {
                        timeline.pause();
                        gameOn = false;
                        startButton.setText("CONTINUE");
                    }
                }
            }
        });

        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                giveUpButton.fire();
                board.resetCheckers();
                gameOver = false;
                gameDurationText.setText("");
                seconds = 0;
                minutes = 0;
            }
        });

        giveUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameOn) {
                    timeline.pause();
                    if (turnWhite) {
                        gameOverText.setText("GAME OVER! RED WON!");
                        noOfGamesWonByRed++;
                        redWins.setText("RED wins: " + noOfGamesWonByRed);
                    } else {
                        gameOverText.setText("GAME OVER! WHITE WON!");
                        noOfGamesWonByWhite++;
                        whiteWins.setText("WHITE wins: " + noOfGamesWonByWhite);
                    }
                }
                gameOn = false;
                gameOver = true;
                System.out.println(seconds);
                System.out.println(seconds/10);
                stopwatch.setText("00:00");
                gameDurationText.setText("Game duration: " + ((((minutes/10) == 0) ? "0" : "") + minutes + ":"
                        + (((seconds/10) == 0) ? "0" : "") + seconds));
                startButton.setText("START");
            }
        });

        printCheckers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Checkers from the board:");
                    for (Tile[] tileRow: board.getBoard()) {
                        for(Tile tile: tileRow) {
                            if (tile.hasChecker()) {
                                System.out.println(tile.getChecker());
                            }
                        }
                    }
        }
        });

        printTiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Tiles from the board:");
                for (Tile[] tileRow: board.getBoard()) {
                    for(Tile tile: tileRow) {
                        System.out.println(tile);
                    }
                }
            }
        });

        printGroupOfCheckers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Checkers from the group:");
                for (Node checker: checkersGroup.getChildren()) {
                    System.out.println(checker);
                }
            }
        });

        printGroupOfTiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Tiles from the group:");
                for (Node tile: tilesGroup.getChildren()) {
                    System.out.println(tile);
                }
            }
        });

        printPossibleMoves.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Possible Moves Making Queen:");
                for(Move move: possibleMovesMakingQueen) {
                    System.out.println(move);
                }
                System.out.println("Possible Moves:");
                for(Move move: possibleMoves) {
                    System.out.println(move);
                }
                System.out.println("Possible Kills Making Queen:");
                for(Move move: possibleKillsMakingQueen) {
                    System.out.println(move);
                }
                System.out.println("Possible Kills:");
                for(Move move: possibleKills) {
                    System.out.println(move);
                }
                System.out.println("Possible Moves by Queen:");
                for(Move move: possibleMovesByQueen) {
                    System.out.println(move);
                }
                System.out.println("Possible Kills by Queen:");
                for(Move move: possibleKillsByQueen) {
                    System.out.println(move);
                }
            }
        });

    }
}
