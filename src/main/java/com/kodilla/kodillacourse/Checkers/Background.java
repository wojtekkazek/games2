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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static com.kodilla.kodillacourse.Checkers.Board.checkersGroup;
import static com.kodilla.kodillacourse.Checkers.Board.tilesGroup;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;

public class Background {

    private Pane content;
    private Scene scene;
    private HBox bigHBox;

    public static Board board = new Board();

    private VBox vbox;
    private Button startButton;

    private Button printCheckers;
    private Button printTiles;
    private Button printGroupOfCheckers;
    private Button printGroupOfTiles;
    private Button printPossibleMoves;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(400, 400);
        root.getChildren().addAll(board.getTilesGroup());
        root.getChildren().addAll(board.getCheckersGroup());
        return root;
    }

    public void setBackground(Stage primaryStage) {
        content = new Pane(createContent());
        primaryStage.setTitle("Checkers");

        startButton = new Button("START");
        printCheckers = new Button("PRINT CHECKERS");
        printTiles = new Button("PRINT TILES");
        printGroupOfCheckers = new Button("PRINT GROUP OF CHECKERS");
        printGroupOfTiles = new Button("PRINT GROUP OF TILES");
        printPossibleMoves = new Button("PRINT MOVES");

        vbox = new VBox(10, startButton, new Text("something"), new Text("something else"),
                printCheckers, printTiles, printGroupOfCheckers, printGroupOfTiles, printPossibleMoves);

        bigHBox = new HBox(10, content, vbox);
        scene = new Scene(bigHBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void activateButtons() {
//        startButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                board.getCheckersList().get(0).move(101,199);
////                board.identifyTileByCoordinates(323,258);
////                for(Checker checker: board.getCheckersList()) {
////                    System.out.println(checker.getTile());
////                }
//            }
//        });

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
