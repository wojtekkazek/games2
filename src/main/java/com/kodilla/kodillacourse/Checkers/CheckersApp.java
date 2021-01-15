package com.kodilla.kodillacourse.Checkers;

import javafx.application.Application;
import javafx.stage.Stage;

import static com.kodilla.kodillacourse.Checkers.Background.board;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.analyzeBoard;

public class CheckersApp extends Application {

    Background background = new Background();

    @Override
    public void start(Stage primaryStage) {
       background.setBackground(primaryStage);
       board.setCheckers();
       background.activateButtons();
       analyzeBoard();
//       //printing all tiles
//       for (Tile[] tileRow: background.getBoard().getBoard()) {
//           for (Tile tile: tileRow) {
//               System.out.println(tile);
//           }
//       }
       //printing tiles where checkers are present
//       for(Checker checker: background.getBoard().getCheckersList()) {
//           System.out.println(checker.getTile());
//       }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
