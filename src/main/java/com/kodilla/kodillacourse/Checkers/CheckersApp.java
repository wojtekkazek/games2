package com.kodilla.kodillacourse.Checkers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CheckersApp extends Application {

    private Pane content;
    private Button startButton;
    private ChoiceBox levelChoice;
    private HBox startPlusLevel;
    private Text stopwatch;
    private int mins;
    private int secs;
    private boolean gameOn;
    private Timeline timeline;
    private Text turn;
    private Text checkersNoWhite;
    private Text noOfWhiteCheckers;
    private Text checkersNoRed;
    private Text noOfRedCheckers;
    private Text gamePaused;
    private Button giveUpButton;
    private Text gameOver;
    private int whiteWonNo;
    private Text whiteWon = new Text("");
    private int redWonNo;
    private Text redWon = new Text("");
    private Text gameDuration = new Text("");
    private Text averageDuration = new Text("");
    private Button moveRed;
    private VBox vbox;
    private HBox bigHBox;
    private Scene scene;

    public static final int tileSize = 50;
    public static final int width = 8;
    public static final int height = 8;
    private boolean turnWhite = true;
    private DifficultyLevel difficultyLevel = DifficultyLevel.VERYEASY;

    private Tile[][] board = new Tile [width][height];

    private Group tilesGroup = new Group();
    private Group checkersGroup = new Group();
    private List<Checker> checkersList = new ArrayList<>();
    private List<Checker> queensList = new ArrayList<>();
    private List<Move> possibleKillsOfQueenOrToMakeQueen = new ArrayList<>();
    private List<Move> possibleKills = new ArrayList<>();
    private List<Move> possibleMovesToMakeQueen = new ArrayList<>();
    private List<Move> possibleSafeMoves = new ArrayList<>();
    private List<Move> possibleMoves = new ArrayList<>();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(width * tileSize, height * tileSize);
        root.getChildren().addAll(tilesGroup, checkersGroup);
        setTilesAndCheckers();
        return root;
    }

    public void setTilesAndCheckers() {
        for (int y = 0; y < height; y++) {
            for (int x=0; x < width; x++) {
                Tile tile = new Tile(tileSize, (x+y)%2 == 0, x, y);
                board[x][y] = tile;

                tilesGroup.getChildren().add(tile);

                Checker checker = null;

                if (y <= 2 && (x+y)%2 != 0) {
                    checker = makeChecker(CheckerType.RED, x, y, checkersList, queensList);
                }

                if (y >= 5 && (x+y)%2 != 0) {
                    checker = makeChecker(CheckerType.WHITE, x, y, checkersList, queensList);
                }

                if (checker != null) {
                    tile.setChecker(checker);
                    checkersGroup.getChildren().add(checker);
                    checkersList.add(checker);
                }

            }
        }
    }


    private MoveResult tryMove(Checker checker, int newX, int newY) {

        if (!gameOn) {
            gamePaused.setText("GAME PAUSED!");
            return new MoveResult(MoveType.NONE);
        }

        if (board[newX][newY].hasChecker() || (newX + newY) %2 == 0) {
            return new MoveResult(MoveType.NONE);
        }

        if ((turnWhite && checker.getType() != CheckerType.WHITE) || (!(turnWhite) && checker.getType() == CheckerType.WHITE)) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(checker.getOldX());
        int y0 = toBoard(checker.getOldY());

        if (checker.getIfIsQueen() == false) {

            if (Math.abs(newX - x0) == 1 && newY - y0 == checker.getType().moveDir) {
                if (!anyCheckerOfTypeCanKill(checkersList, checker.getType())) {
                    if (!anyQueenOfTypeCanKill(queensList, checker.getType())) {
                        return new MoveResult(MoveType.NORMAL);
                    }
                }
            }

            if (Math.abs(newX - x0) == 2 && newY - y0 == checker.getType().moveDir * 2) {

                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY -y0) / 2;

                if (board[x1][y1].hasChecker() && board[x1][y1].getChecker().getType() != checker.getType()) {
                    return new MoveResult(MoveType.KILL, board[x1][y1].getChecker());
                }
            }

            return new MoveResult((MoveType.NONE));

        } else {

            if (Math.abs(newX - x0) != 0 && Math.abs(newY - y0) == Math.abs(newX - x0)){

                boolean isCheckerOnAWay = false;
                for (int i=1; i<Math.abs(newX - x0); i++) {
                    int x1 = x0 + i*(newX - x0) / Math.abs(newX - x0);
                    int y1 = y0 + i*(newY -y0) / Math.abs(newY -y0);
                    if (board[x1][y1].hasChecker()) {
                        isCheckerOnAWay = true;
                    }
                }

                CheckerType checkerType = CheckerType.valueOf(checker.getType().name());

                if (!isCheckerOnAWay) {
                    if (!anyQueenOfTypeCanKill(queensList, checker.getType())) {
                        if (!anyCheckerOfTypeCanKill(checkersList, checkerType)) {
                            return new MoveResult(MoveType.NORMAL);
                        }
                    }
                } else {

                    boolean isCheckerOnlyOnSecondToLastTile = true;
                    for (int i = 1; i < Math.abs(newX - x0) - 1; i++) {
                        int x1 = x0 + i * (newX - x0) / Math.abs(newX - x0);
                        int y1 = y0 + i * (newY - y0) / Math.abs(newY - y0);
                        if (board[x1][y1].hasChecker()) {
                            isCheckerOnlyOnSecondToLastTile = false;
                        }
                    }

                    int x2 = newX - (newX - x0) / Math.abs(newX - x0);
                    int y2 = newY - (newY - y0) / Math.abs(newY - y0);

                    boolean isCheckerOnSecondToLastTileDifferentType = false;
                    if (isCheckerOnlyOnSecondToLastTile) {
                        if (board[x2][y2].getChecker().getType() != checker.getType()) {
                            isCheckerOnSecondToLastTileDifferentType = true;
                        }
                    }

                    if (isCheckerOnSecondToLastTileDifferentType) {
                        return new MoveResult(MoveType.KILL, board[x2][y2].getChecker());
                    }

                }

            }

            return new MoveResult((MoveType.NONE));
        }

    }

    private int toBoard(double a) {
        return (int)(a + tileSize / 2) / tileSize;
    }

    private Checker makeChecker(CheckerType type, int x, int y, List<Checker> checkersList, List<Checker> queensList) {
        Checker checker = new Checker(type, false, x, y);

        checker.setOnMouseReleased(e -> {
            int newX = toBoard(checker.getLayoutX());
            int newY = toBoard(checker.getLayoutY());

            MoveResult result = tryMove(checker, newX, newY);

            int x0 = toBoard(checker.getOldX());
            int y0 = toBoard(checker.getOldY());

            switch (result.getType()) {
                case NONE:
                    checker.abortMove();
                    break;

                case NORMAL:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);
                    if (newY == 7 || newY == 0) {
                        checker.transformToQueen();
                        checkersList.remove(checker);
                        queensList.add(checker);
                    }
                    turnWhite = !turnWhite;
                    updateStatistics();
                    break;

                case KILL:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);
                    if (newY == 7 || newY == 0) {
                        checker.transformToQueen();
                        checkersList.remove(checker);
                        queensList.add(checker);
                    }

                    Checker killedChecker = result.getChecker();
                    board[toBoard(killedChecker.getOldX())][toBoard(killedChecker.getOldY())].setChecker(null);
                    checkersGroup.getChildren().remove(killedChecker);
                    if (killedChecker.getIfIsQueen()) {
                        queensList.remove(killedChecker);
                        if(!queenCanKill(checker)) {
                            turnWhite = !turnWhite;
                        }
                    } else {
                        checkersList.remove(killedChecker);
                        if(!checkerCanKill(checker)) {
                            turnWhite = !turnWhite;
                        }
                    }

                    updateStatistics();
                    break;
            }
        });

        return checker;
    }

    public boolean tileExists(int x, int y) throws NullPointerException {
        return (x >=0 && x <= 7 && y >=0 && y <= 7);
    }

    public boolean checkerCanMove(Checker checker) {

        int x0 = toBoard(checker.getOldX());
        int y0 = toBoard(checker.getOldY());
        int possibleMovesOfChecker = 0;

        if (tileExists(x0+1,y0 + checker.getType().moveDir)
                && !board[x0 + 1][y0 + checker.getType().moveDir].hasChecker()) {
            possibleMoves.add(new Move(checker, x0+1, y0 + checker.getType().moveDir));
            possibleMovesOfChecker++;
            if (y0 + checker.getType().moveDir == 4 + 4 * checker.getType().moveDir) {
                possibleMovesToMakeQueen.add(new Move(checker, x0+1, y0 + checker.getType().moveDir));
            }
        }

        if (tileExists(x0-1,y0 + checker.getType().moveDir)
                && !board[x0-1][y0 + checker.getType().moveDir].hasChecker()) {
            possibleMoves.add(new Move(checker, x0-1, y0 + checker.getType().moveDir));
            possibleMovesOfChecker++;
            if (y0 + checker.getType().moveDir == 4 + 4 * checker.getType().moveDir) {
                possibleMovesToMakeQueen.add(new Move(checker, x0+1, y0 + checker.getType().moveDir));
            }
        }

        if(possibleMovesOfChecker > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean anyCheckerOfTypeCanMove(List<Checker> checkers, CheckerType type) {
        List<Checker> checkersWhichCanMove = checkers.stream()
                .filter(checker -> checker.getType() == type)
                .filter(checker -> checkerCanMove(checker) == true)
                .collect(Collectors.toList());
        if (checkersWhichCanMove.size() != 0) {
            return true;
        }
        return false;
    }

    public boolean checkerCanMoveSafely(Checker checker) {

        int x0 = toBoard(checker.getOldX());
        int y0 = toBoard(checker.getOldY());
        int possibleSafeMovesOfChecker = 0;

        if (!tileExists(x0+1,y0 + checker.getType().moveDir)) {
        } else if (tileExists(x0+1,y0 + checker.getType().moveDir) && board[x0 + 1][y0 + checker.getType().moveDir].hasChecker()) {
        } else if (tileExists(x0+2,y0 + 2 * checker.getType().moveDir) && board[x0 + 2][y0 + 2 * checker.getType().moveDir].hasChecker()
        && !board[x0 + 2][y0 + 2 * checker.getType().moveDir].getChecker().getType().equals(checker.getType())) {
        } else if (tileExists(x0,y0 + 2 * checker.getType().moveDir) && board[x0][y0 + 2 * checker.getType().moveDir].hasChecker()
                && !board[x0][y0 + 2 * checker.getType().moveDir].getChecker().getType().equals(checker.getType())
                && tileExists(x0 + 2,y0) && !board[x0 + 2][y0].hasChecker()) {
        } else {
            possibleSafeMoves.add(new Move(checker, x0+1, y0 + checker.getType().moveDir));
            possibleSafeMovesOfChecker++;
        }


        if (!tileExists(x0-1,y0 + checker.getType().moveDir)) {
        } else if (tileExists(x0-1,y0 + checker.getType().moveDir) && board[x0 - 1][y0 + checker.getType().moveDir].hasChecker()) {
        } else if (tileExists(x0-2,y0 + 2 * checker.getType().moveDir) && board[x0 - 2][y0 + 2 * checker.getType().moveDir].hasChecker()
                && !board[x0 - 2][y0 + 2 * checker.getType().moveDir].getChecker().getType().equals(checker.getType())) {
        } else if (tileExists(x0,y0 + 2 * checker.getType().moveDir) && board[x0][y0 + 2 * checker.getType().moveDir].hasChecker()
                && !board[x0][y0 + 2 * checker.getType().moveDir].getChecker().getType().equals(checker.getType())
                && tileExists(x0 - 2,y0) && !board[x0 - 2][y0].hasChecker()) {
        } else {
            possibleSafeMoves.add(new Move(checker, x0-1, y0 + checker.getType().moveDir));
            possibleSafeMovesOfChecker++;
        }

        if(possibleSafeMovesOfChecker > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean anyCheckerOfTypeCanMoveSafely(List<Checker> checkers, CheckerType type) {
        List<Checker> checkersWhichCanMoveSafely = checkers.stream()
                .filter(checker -> checker.getType() == type)
                .filter(checker -> checkerCanMoveSafely(checker) == true)
                .collect(Collectors.toList());
        if (checkersWhichCanMoveSafely.size() != 0) {
            return true;
        }
        return false;
    }

    public boolean checkerCanKill(Checker checker) {

        int x0 = toBoard(checker.getOldX());
        int y0 = toBoard(checker.getOldY());
        int possibleKillsOfChecker = 0;

        if (tileExists(x0+1,y0 + checker.getType().moveDir)
                && tileExists(x0+2,y0 + 2 * checker.getType().moveDir)
                && board[x0 + 1][y0 + checker.getType().moveDir].hasChecker()
                && board[x0 + 1][y0 + checker.getType().moveDir].getChecker().getType() != checker.getType()
                && !board[x0 + 2][y0 + 2 * checker.getType().moveDir].hasChecker()) {
            possibleKills.add(new Move(checker, x0+2, y0 + 2 * checker.getType().moveDir));
            possibleKillsOfChecker++;
            if (y0 + 2 * checker.getType().moveDir == 4 + 4 * checker.getType().moveDir) {
                possibleKillsOfQueenOrToMakeQueen.add(new Move(checker, x0+2, y0 + 2 * checker.getType().moveDir));
            } else if (board[x0 + 1][y0 + checker.getType().moveDir].getChecker().getIfIsQueen()) {
                possibleKillsOfQueenOrToMakeQueen.add(new Move(checker, x0+2, y0 + 2 * checker.getType().moveDir));
            }
        }

        if (tileExists(x0-1,y0 + checker.getType().moveDir)
                && tileExists(x0-2,y0 + 2 * checker.getType().moveDir)
                && board[x0 - 1][y0 + checker.getType().moveDir].hasChecker()
                && board[x0 - 1][y0 + checker.getType().moveDir].getChecker().getType() != checker.getType()
                && !board[x0 - 2][y0 + 2 * checker.getType().moveDir].hasChecker()) {
            possibleKills.add(new Move(checker, x0-2, y0 + 2 * checker.getType().moveDir));
            possibleKillsOfChecker++;
            if (y0 + 2 * checker.getType().moveDir == 4 + 4 * checker.getType().moveDir) {
                possibleKillsOfQueenOrToMakeQueen.add(new Move(checker, x0-2, y0 + 2 * checker.getType().moveDir));
            } else if (board[x0 - 1][y0 + checker.getType().moveDir].getChecker().getIfIsQueen()) {
                possibleKillsOfQueenOrToMakeQueen.add(new Move(checker, x0-2, y0 + 2 * checker.getType().moveDir));
            }
        }

        if(possibleKillsOfChecker > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean anyCheckerOfTypeCanKill(List<Checker> checkers, CheckerType type) {
        List<Checker> checkersWhichCanKill = checkers.stream()
                .filter(checker -> checker.getType() == type)
                .filter(checker -> checkerCanKill(checker) == true)
                .collect(Collectors.toList());
        if (checkersWhichCanKill.size() != 0) {
            return true;
        }
        return false;
    }

    public List<Tile> whereQueenCanPotentiallyMove (Checker queen) {

        List<Tile> potentialTiles = new ArrayList<>();

        int x0 = toBoard(queen.getOldX());
        int y0 = toBoard(queen.getOldY());

        for (int i=-7; i<8; i++) {
            if (i != 0) {
                if (tileExists(x0 + i, y0 + i)) {
                    potentialTiles.add(new Tile(tileSize, false, x0 + i, y0 + i));
                }
                if (tileExists(x0 - i, y0 + i)) {
                    potentialTiles.add(new Tile(tileSize, false, x0 - i, y0 + i));
                }
            }
        }
        return potentialTiles;
    }

    public boolean queenCanKill(Checker queen) {

        List<Tile> potentialTiles = whereQueenCanPotentiallyMove(queen);

        int x0 = toBoard(queen.getOldX());
        int y0 = toBoard(queen.getOldY());
        int possibleKillsOfQueen = 0;

        for (Tile potentialTile: potentialTiles) {
            int newX = potentialTile.getTileX();
            int newY = potentialTile.getTileY();

            if (!board[newX][newY].hasChecker()) {
                boolean isCheckerOnAWay = false;
                for (int i = 1; i < Math.abs(newX - x0); i++) {
                    int x1 = x0 + i * (newX - x0) / Math.abs(newX - x0);
                    int y1 = y0 + i * (newY - y0) / Math.abs(newY - y0);
                    if (board[x1][y1].hasChecker()) {
                        isCheckerOnAWay = true;
                    }
                }

                if (isCheckerOnAWay) {
                    boolean isCheckerOnlyOnSecondToLastTile = true;
                    for (int i = 1; i < Math.abs(newX - x0) - 1; i++) {
                        int x1 = x0 + i * (newX - x0) / Math.abs(newX - x0);
                        int y1 = y0 + i * (newY - y0) / Math.abs(newY - y0);
                        if (board[x1][y1].hasChecker()) {
                            isCheckerOnlyOnSecondToLastTile = false;
                        }
                    }

                    int x2 = newX - (newX - x0) / Math.abs(newX - x0);
                    int y2 = newY - (newY - y0) / Math.abs(newY - y0);

                    boolean isCheckerOnSecondToLastTileDifferentType = false;
                    if (isCheckerOnlyOnSecondToLastTile) {
                        if (board[x2][y2].getChecker().getType() != queen.getType()) {
                            isCheckerOnSecondToLastTileDifferentType = true;
                        }
                    }

                    if (isCheckerOnSecondToLastTileDifferentType) {
                        possibleKills.add(new Move(queen, newX, newY));
                        possibleKillsOfQueen++;
                        if (board[x2][y2].getChecker().getIfIsQueen()) {
                            possibleKillsOfQueenOrToMakeQueen.add(new Move(queen, newX, newY));
                        }
                    }
                } else {
                    possibleMoves.add(new Move(queen, newX, newY));
                }
            }
        }
        if (possibleKillsOfQueen > 0) {
            return true;
        }
        return false;
    }

    public boolean anyQueenOfTypeCanKill(List<Checker> queens, CheckerType type) {
        List<Checker> queensWhichCanKill = queens.stream()
                .filter(queen -> queen.getType() == type)
                .filter(queen -> queenCanKill(queen) == true)
                .collect(Collectors.toList());

        if (queensWhichCanKill.size() != 0) {
            return true;
        }
        return false;
    }

    public void MoveAsComputer(List<Checker> checkers, List<Checker> queens) {
        anyCheckerOfTypeCanKill(checkers, CheckerType.RED);
        anyQueenOfTypeCanKill(queens, CheckerType.RED);
        anyCheckerOfTypeCanMoveSafely(checkers, CheckerType.RED);
        anyCheckerOfTypeCanMove(checkers, CheckerType.RED);
        Random randomGenerator = new Random();
        Move randomMove;

        if (!difficultyLevel.equals(DifficultyLevel.VERYEASY) && possibleKillsOfQueenOrToMakeQueen.size() > 0) {
            randomMove = possibleKillsOfQueenOrToMakeQueen.get(randomGenerator.nextInt(possibleKillsOfQueenOrToMakeQueen.size()));
        } else if (possibleKills.size() > 0) {
            randomMove = possibleKills.get(randomGenerator.nextInt(possibleKills.size()));
        } else if (!difficultyLevel.equals(DifficultyLevel.VERYEASY) && possibleMovesToMakeQueen.size() > 0) {
            randomMove = possibleMovesToMakeQueen.get(randomGenerator.nextInt(possibleMovesToMakeQueen.size()));
        } else if (!difficultyLevel.equals(DifficultyLevel.VERYEASY) && possibleSafeMoves.size() > 0) {
            randomMove = possibleSafeMoves.get(randomGenerator.nextInt(possibleSafeMoves.size()));
        } else {
            randomMove = possibleMoves.get(randomGenerator.nextInt(possibleMoves.size()));
        }

        if (!turnWhite) {
            Checker checker = randomMove.getChecker();
            int x0 = toBoard(checker.getOldX());
            int y0 = toBoard(checker.getOldY());
            int newX = randomMove.getNewX();
            int newY = randomMove.getNewY();
            MoveResult result = tryMove(checker, newX, newY);

            switch (result.getType()) {
                case NONE:
                    checker.abortMove();
                    break;

                case NORMAL:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);
                    if (newY == 7 || newY == 0) {
                        checker.transformToQueen();
                        checkersList.remove(checker);
                        queensList.add(checker);
                    }
                    turnWhite = !turnWhite;
                    updateStatistics();
                    break;

                case KILL:

                    checker.move(newX, newY);
                    board[x0][y0].setChecker(null);
                    board[newX][newY].setChecker(checker);
                    if (newY == 7 || newY == 0) {
                        checker.transformToQueen();
                        checkersList.remove(checker);
                        queensList.add(checker);
                    }

                    Checker killedChecker = result.getChecker();
                    board[toBoard(killedChecker.getOldX())][toBoard(killedChecker.getOldY())].setChecker(null);
                    checkersGroup.getChildren().remove(killedChecker);
                    if (killedChecker.getIfIsQueen()) {
                        queensList.remove(killedChecker);
                        if(!queenCanKill(checker)) {
                            turnWhite = !turnWhite;
                        }
                    } else {
                        checkersList.remove(killedChecker);
                        if(!checkerCanKill(checker)) {
                            turnWhite = !turnWhite;
                        }
                    }

                    updateStatistics();
                    break;
            }

        }
    }

    public void updateStatistics() {
        if (turnWhite) {
            turn.setText("Turn: WHITE");
        } else {
            turn.setText("Turn: RED");
        }

        List<Checker> whiteCheckers = checkersList.stream()
                .filter(checker -> checker.getType() == CheckerType.WHITE)
                .collect(Collectors.toList());
        //noOfWhiteCheckers.setText(String.valueOf(whiteCheckers.size()));
        //noOfRedCheckers.setText(String.valueOf(checkersList.size()-whiteCheckers.size()));
        List<Checker> whiteQueens = queensList.stream()
                .filter(queen -> queen.getType() == CheckerType.WHITE)
                .collect(Collectors.toList());
        noOfWhiteCheckers.setText(whiteCheckers.size() + " / " + whiteQueens.size());
        noOfRedCheckers.setText((checkersList.size()- whiteCheckers.size()) + " / " + (queensList.size()- whiteQueens.size()));

        if (whiteCheckers.isEmpty() && whiteQueens.isEmpty()) {
            turnWhite = true;
            giveUpButton.fire();
        }
        if ((checkersList.size()- whiteCheckers.size() == 0) && (queensList.size()- whiteQueens.size() == 0)) {
            turnWhite = false;
            giveUpButton.fire();
        }

    }

    void runStopwatch (Text text) {
        if(secs == 60) {
            mins++;
            secs = 0;
        }
        stopwatch.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs++);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        content = new Pane(createContent());

        startButton = new Button("START");
        levelChoice = new ChoiceBox();
        levelChoice.getItems().add("VERYEASY");
        levelChoice.getItems().add("EASY");
        levelChoice.setValue("VERYEASY");
        startPlusLevel = new HBox(10, startButton, levelChoice);

        mins = 0;
        secs = 0;
        gameOn = false;
        stopwatch = new Text("00:00");
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                runStopwatch(stopwatch);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                difficultyLevel = DifficultyLevel.valueOf(levelChoice.getValue().toString());
                gameOver.setText("");
                if(gameOn) {
                    timeline.pause();
                    gameOn = false;
                    startButton.setText("CONTINUE");
                } else {
                    timeline.play();
                    gameOn = true;
                    startButton.setText("PAUSE");
                    gamePaused.setText("");
                }
            }
        });

        giveUpButton = new Button("Give up");
        giveUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(startButton.getText() != "START") {
                    timeline.pause();
                    String whoWon;
                    if(turnWhite) {
                        whoWon = "RED";
                        redWonNo = redWonNo + 1;
                        redWon.setText("Red won: " + redWonNo + " games");
                    } else {
                        whoWon = "WHITE";
                        whiteWonNo = whiteWonNo + 1;
                        whiteWon.setText("White won: " + whiteWonNo + " games");
                    }
                    gameOver.setText("GAME OVER! " + whoWon +  " WON!");

                    gameDuration.setText("Game duration: " + ((((mins/10) == 0) ? "0" : "") + mins + ":"
                            + (((secs/10) == 0) ? "0" : "") + secs++));
                    /*lastGameMins = mins;
                    lastGameSecs = secs;
                    totalSecs = totalSecs + secs;
                    totalMins = totalMins + mins;
                    averageDuration.setText("Average game duration: " + ((((totalMins/10) == 0) ? "0" : "") + totalMins + ":"
                            + (((totalSecs/10) == 0) ? "0" : "") + totalSecs++));*/

                    gameOn = false;
                    turnWhite = true;
                    mins = 0;
                    secs = 0;
                    stopwatch.setText("00:00");
                    startButton.setText("START");
                    tilesGroup.getChildren().clear();
                    checkersGroup.getChildren().clear();
                    checkersList.clear();
                    queensList.clear();
                    setTilesAndCheckers();
                    updateStatistics();
                }
            }
        });

        moveRed = new Button("Move RED");
        moveRed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MoveAsComputer(checkersList, queensList);
                possibleKills.clear();
                possibleMoves.clear();
                possibleSafeMoves.clear();
                possibleKillsOfQueenOrToMakeQueen.clear();
                possibleMovesToMakeQueen.clear();
            }
        });


        turn = new Text("Turn: WHITE");
        checkersNoWhite = new Text("White checkers / queens remaining:");
        noOfWhiteCheckers = new Text("12 / 0 ");
        checkersNoRed = new Text("Red checkers / queens remaining:");
        noOfRedCheckers = new Text("12 / 0 ");
        gamePaused = new Text();
        gameOver = new Text();
        gameOver.setFill(Color.RED);
        vbox = new VBox(10, startPlusLevel, stopwatch, turn, checkersNoWhite,noOfWhiteCheckers,checkersNoRed,noOfRedCheckers,
                gamePaused, giveUpButton, gameOver, gameDuration, whiteWon, redWon, averageDuration, moveRed);
        bigHBox = new HBox(10, content, vbox);
        scene = new Scene(bigHBox);
        scene.setCursor(Cursor.HAND);
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
