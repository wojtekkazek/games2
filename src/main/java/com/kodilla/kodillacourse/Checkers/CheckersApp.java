package com.kodilla.kodillacourse.Checkers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.kodilla.kodillacourse.Checkers.Background.board;
import static com.kodilla.kodillacourse.Checkers.Background.stopwatch;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.analyzeBoard;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.updateHighlighting;

public class CheckersApp extends Application {

    public static Timeline timeline;
    Background background = new Background();

    public static boolean gameOver = false;
    public static boolean gameOn = false;
    public static boolean turnWhite = true;
    public static boolean isBetweenKills = false;
    public static boolean isSimulationOngoing = false;
    public static boolean modeSinglePlayer = false;
    public static DifficultyLevel difficultyLevel;

    @Override
    public void start(Stage primaryStage) {
       background.setBackground(primaryStage);
       board.setCheckers();
       background.activateButtons();

        timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                background.runStopwatch(stopwatch);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
