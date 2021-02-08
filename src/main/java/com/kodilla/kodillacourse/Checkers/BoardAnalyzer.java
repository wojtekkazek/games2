package com.kodilla.kodillacourse.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kodilla.kodillacourse.Checkers.Background.board;
import static com.kodilla.kodillacourse.Checkers.Board.identifyTile;
import static com.kodilla.kodillacourse.Checkers.Board.tilesToHighlight;
import static com.kodilla.kodillacourse.Checkers.CheckersApp.*;
import static com.kodilla.kodillacourse.Checkers.MoveExecutor.*;

public class BoardAnalyzer {

    public static int noOfWhiteCheckers;
    public static int noOfWhiteQueens;
    public static int noOfRedCheckers;
    public static int noOfRedQueens;

    public static List<Checker> checkersOfType;

    public static List<Move> possibleMoves;
    public static List<Move> possibleMovesMakingQueen;
    public static List<Move> possibleKills;
    public static List<Move> possibleKillsMakingQueen;
    public static List<Move> possibleMovesByQueen;
    public static List<Move> possibleKillsByQueen;
    public static List<Move> possibleKillsKillingQueen;
    public static List<Move> possibleKillsKillingQueenAndMakingQueen;
    public static List<Move> possibleKillsKillingQueenAndNotGettingKilledAfter;
    public static List<Move> possibleKillsAndNotGettingKilledAfter;
    public static List<Move> possibleMovesAndNotGettingKilledAfter;
    public static List<Move> possibleKillsAndGettingKilledAfter;
    public static List<Move> possibleMovesAndGettingKilledAfter;
    private static List<Checker> checkersPossibleToBeKilled;

    public static CheckerType correctType;

    public static void analyzeBoard() {
        updateQuantities();
        possibleMoves = new ArrayList<>();
        possibleMovesMakingQueen = new ArrayList<>();
        possibleKills = new ArrayList<>();
        possibleKillsMakingQueen = new ArrayList<>();
        possibleMovesByQueen = new ArrayList<>();
        possibleKillsByQueen = new ArrayList<>();
        possibleKillsKillingQueen = new ArrayList<>();
        possibleKillsKillingQueenAndMakingQueen = new ArrayList<>();
        checkersPossibleToBeKilled = new ArrayList<>();

        if (turnWhite) {
            correctType = CheckerType.WHITE;
        } else {
            correctType = CheckerType.RED;
        }
        findAllCheckersOfType(correctType);
        findPossibleMoves();
        findPossibleKills();
        findPossibleMovesByQueen();
        findPossibleKillsByQueen();

        updateHighlighting();

        if (!isSimulationOngoing) {
            if (difficultyLevel == DifficultyLevel.HARD) {
                possibleKillsKillingQueenAndNotGettingKilledAfter = new ArrayList<>();
                possibleKillsAndNotGettingKilledAfter = new ArrayList<>();
                possibleMovesAndNotGettingKilledAfter = new ArrayList<>();
                findWhenCheckersWontGetKilledAfterMove();
            }
            if (difficultyLevel == DifficultyLevel.VERYEASY) {
                possibleKillsAndGettingKilledAfter = new ArrayList<>();
                possibleMovesAndGettingKilledAfter = new ArrayList<>();
                findWhenCheckersWillGetKilledAfterMove();
            }
        }
    }

    public static void findAllCheckersOfType(CheckerType type) {
        checkersOfType = new ArrayList<>();
        for (Tile[] tileRow: board.getBoard()) {
            for(Tile tile: tileRow) {
                if (tile.hasChecker() && tile.getChecker().getType() == type) {
                    checkersOfType.add(tile.getChecker());
                }
            }
        }
    }

    public static void updateQuantities() {
        noOfWhiteCheckers = 0;
        noOfWhiteQueens=0;
        noOfRedCheckers=0;
        noOfRedQueens=0;
        findAllCheckersOfType(CheckerType.WHITE);
        noOfWhiteCheckers = checkersOfType.stream()
                .filter(c -> !c.isQueen())
                .collect(Collectors.toList()).size();
        noOfWhiteQueens = checkersOfType.size() - noOfWhiteCheckers;
        findAllCheckersOfType(CheckerType.RED);
        noOfRedCheckers = checkersOfType.stream()
                .filter(c -> !c.isQueen())
                .collect(Collectors.toList()).size();
        noOfRedQueens = checkersOfType.size() - noOfRedCheckers;
    }

    public static void updateHighlighting() {
        tilesToHighlight.getChildren().clear();
        List<Move> allPossibleMovesIncludingKills = new ArrayList<>();
        allPossibleMovesIncludingKills.addAll(possibleKills);
        allPossibleMovesIncludingKills.addAll(possibleKillsByQueen);
        if (allPossibleMovesIncludingKills.size() == 0) {
            allPossibleMovesIncludingKills.addAll(possibleMoves);
            allPossibleMovesIncludingKills.addAll(possibleMovesByQueen);
        }
        for (Move move: allPossibleMovesIncludingKills) {
            tilesToHighlight.getChildren().add(new Highlighting(move.getNewTile().getTileX(), move.getNewTile().getTileY()));
        }
        if (isBetweenKills) {
            tilesToHighlight.getChildren().clear();
            List<Move> followingKillsToHighlight = allPossibleMovesIncludingKills.stream()
                    .filter(m -> m.getMoveType() == MoveType.KILL || m.getMoveType() == MoveType.QUEENKILL)
                    .filter(m -> m.getChecker() == lastKiller)
                    .collect(Collectors.toList());
            for (Move move: followingKillsToHighlight) {
                tilesToHighlight.getChildren().add(new Highlighting(move.getNewTile().getTileX(), move.getNewTile().getTileY()));
            }
        }
    }

    public static void findPossibleMoves() {
        for (Checker checker: checkersOfType) {
            if (!checker.isQueen()) {
                int x = checker.getTile().getTileX();
                int y = checker.getTile().getTileY();

                Tile tileOfPotentialMove1 = identifyTile(x+1, y+correctType.getMoveDir());
                if (tileOfPotentialMove1 != null && !tileOfPotentialMove1.hasChecker()) {
                    Move move = new Move(MoveType.NORMAL, checker, tileOfPotentialMove1);
                    possibleMoves.add(move);
                    if (tileOfPotentialMove1.getTileY() == 0 || tileOfPotentialMove1.getTileY() == 7) {
                        possibleMovesMakingQueen.add(move);
                    }
                }
                Tile tileOfPotentialMove2 = identifyTile(x-1, y+correctType.getMoveDir());
                if (tileOfPotentialMove2 != null && !tileOfPotentialMove2.hasChecker()) {
                    Move move = new Move(MoveType.NORMAL, checker, tileOfPotentialMove2);
                    possibleMoves.add(new Move(MoveType.NORMAL, checker, tileOfPotentialMove2));
                    if (tileOfPotentialMove2.getTileY() == 0 || tileOfPotentialMove2.getTileY() == 7) {
                        possibleMovesMakingQueen.add(move);
                    }
                }
            }
        }
    }

    public static void findPossibleMovesByQueen() {
        for (Checker checker: checkersOfType) {
            if (checker.isQueen()) {
                int x = checker.getTile().getTileX();
                int y = checker.getTile().getTileY();

                List<Tile> allTilesOnDiagonals = new ArrayList<>();
                for (int i = -7; i < 7; i++) {
                    if (i != 0) {
                        Tile tile = identifyTile(x + i, y + i);
                        if (tile != null) {
                            allTilesOnDiagonals.add(tile);
                        }
                        tile = identifyTile(x + i, y - i);
                        if (tile != null) {
                            allTilesOnDiagonals.add(tile);
                        }
                    }
                }

                for(Tile tileOnDiagonal: allTilesOnDiagonals) {
                    List<Tile> tilesOnTheWay = new ArrayList<>();
                    int dirX = (tileOnDiagonal.getTileX() - x)/Math.abs(tileOnDiagonal.getTileX() - x);
                    int dirY = (tileOnDiagonal.getTileY() - y)/Math.abs(tileOnDiagonal.getTileY() - y);
                    for (int j = 1; j < Math.abs(tileOnDiagonal.getTileX() - x) + 1; j++) {
                        tilesOnTheWay.add(identifyTile(x + j * dirX, y + j * dirY));
                    }

                    boolean isTheWayEmpty = true;
                    for (Tile tileOnTheWay: tilesOnTheWay) {
                        if (tileOnTheWay.hasChecker()) {
                            isTheWayEmpty = false;
                        }
                    }
                    if (isTheWayEmpty) {
                        possibleMovesByQueen.add(new Move(MoveType.QUEENMOVE, checker, tileOnDiagonal));
                    }
                }
            }
        }
    }

    public static void findPossibleKills() {
        for (Checker checker: checkersOfType) {
            if (!checker.isQueen()) {
                int x = checker.getTile().getTileX();
                int y = checker.getTile().getTileY();

                Tile tileOfPotentialKill1 = identifyTile(x+1, y+correctType.getMoveDir());
                Tile tileToLandAfterKill1 = identifyTile(x+2, y+correctType.getMoveDir()*2);
                if (tileOfPotentialKill1 != null && tileToLandAfterKill1 != null && tileOfPotentialKill1.hasChecker()) {
                    if (tileOfPotentialKill1.getChecker().getType() != correctType &&
                            !tileToLandAfterKill1.hasChecker()) {
                        Move move = new Move(MoveType.KILL, checker, tileToLandAfterKill1);
                        possibleKills.add(move);
                        checkersPossibleToBeKilled.add(tileOfPotentialKill1.getChecker());
                        if (tileToLandAfterKill1.getTileY() == 0 || tileToLandAfterKill1.getTileY() == 7) {
                            possibleKillsMakingQueen.add(move);
                            if (tileOfPotentialKill1.getChecker().isQueen()) {
                                possibleKillsKillingQueenAndMakingQueen.add(move);
                            }
                        } else if (tileOfPotentialKill1.getChecker().isQueen()) {
                            possibleKillsKillingQueen.add(move);
                        }
                    }
                }

                Tile tileOfPotentialKill2 = identifyTile(x-1, y+correctType.getMoveDir());
                Tile tileToLandAfterKill2 = identifyTile(x-2, y+correctType.getMoveDir()*2);
                if (tileOfPotentialKill2 != null && tileToLandAfterKill2 != null && tileOfPotentialKill2.hasChecker()) {
                    if (tileOfPotentialKill2.getChecker().getType() != correctType &&
                            !tileToLandAfterKill2.hasChecker()) {
                        Move move = new Move(MoveType.KILL, checker, tileToLandAfterKill2);
                        possibleKills.add(move);
                        checkersPossibleToBeKilled.add(tileOfPotentialKill2.getChecker());
                        if (tileToLandAfterKill2.getTileY() == 0 || tileToLandAfterKill2.getTileY() == 7) {
                            possibleKillsMakingQueen.add(move);
                            if (tileOfPotentialKill2.getChecker().isQueen()) {
                                possibleKillsKillingQueenAndMakingQueen.add(move);
                            }
                        } else if (tileOfPotentialKill2.getChecker().isQueen()) {
                            possibleKillsKillingQueen.add(move);
                        }
                    }
                }
            }
        }
    }

    public static void findPossibleKillsByQueen() {
        for (Checker checker: checkersOfType) {
            if (checker.isQueen()) {
                int x = checker.getTile().getTileX();
                int y = checker.getTile().getTileY();

                List<Tile> allTilesOnDiagonals = new ArrayList<>();
                for (int i = -7; i < 7; i++) {
                    if (i != 0) {
                        Tile tile = identifyTile(x + i, y + i);
                        if (tile != null) {
                            allTilesOnDiagonals.add(tile);
                        }
                        tile = identifyTile(x + i, y - i);
                        if (tile != null) {
                            allTilesOnDiagonals.add(tile);
                        }
                    }
                }

                for(Tile tileOnDiagonal: allTilesOnDiagonals) {
                    int dirX = (tileOnDiagonal.getTileX() - x)/Math.abs(tileOnDiagonal.getTileX() - x);
                    int dirY = (tileOnDiagonal.getTileY() - y)/Math.abs(tileOnDiagonal.getTileY() - y);
                    Tile tileToBeKilled = identifyTile(tileOnDiagonal.getTileX() - dirX, tileOnDiagonal.getTileY() - dirY);

                    if (!tileOnDiagonal.hasChecker() &&
                            tileToBeKilled.hasChecker() && tileToBeKilled.getChecker().getType() != checker.getType()) {
                        List<Tile> tilesOnTheWay = new ArrayList<>();
                        for (int j = 1; j < Math.abs(tileToBeKilled.getTileX() - x); j++) {
                            tilesOnTheWay.add(identifyTile(x + j * dirX, y + j * dirY));
                        }

                        boolean isTheWayEmpty = true;
                        for (Tile tileOnTheWay: tilesOnTheWay) {
                            if (tileOnTheWay.hasChecker()) {
                                isTheWayEmpty = false;
                            }
                        }
                        if (isTheWayEmpty) {
                            Move move = new Move(MoveType.QUEENKILL, checker, tileOnDiagonal);
                            possibleKillsByQueen.add(move);
                            checkersPossibleToBeKilled.add(tileToBeKilled.getChecker());
                            if (tileToBeKilled.getChecker().isQueen()) {
                                possibleKillsKillingQueen.add(move);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void findWhenCheckersWontGetKilledAfterMove() {
        isSimulationOngoing = true;
        for (Move move: possibleKillsKillingQueen) {
            if (!simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleKillsKillingQueenAndNotGettingKilledAfter.add(move);
            }
        }
        for (Move move: possibleKills) {
            if (!simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleKillsAndNotGettingKilledAfter.add(move);
            }
        }
        for (Move move: possibleKillsByQueen) {
            if (!simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleKillsAndNotGettingKilledAfter.add(move);
            }
        }
        for (Move move: possibleMoves) {
            if (!simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleMovesAndNotGettingKilledAfter.add(move);
            }
        }
        for (Move move: possibleMovesByQueen) {
            if (!simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleMovesAndNotGettingKilledAfter.add(move);
            }
        }
        isSimulationOngoing = false;
    }

    public static void findWhenCheckersWillGetKilledAfterMove() {
        isSimulationOngoing = true;
        for (Move move: possibleKills) {
            if (simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleKillsAndGettingKilledAfter.add(move);
            }
        }
        for (Move move: possibleMoves) {
            if (simulateMoveAndCheckIfCheckerCanGetKilledAfter(move)) {
                possibleMovesAndGettingKilledAfter.add(move);
            }
        }
        isSimulationOngoing = false;
    }

    public static boolean simulateMoveAndCheckIfCheckerCanGetKilledAfter(Move move) {
        boolean rememberIfTurnWhite = turnWhite;
        executeMove(move);
        analyzeBoard();
        boolean canCheckerBeKilled = canCheckerBeKilled(move.getChecker());
        if (isBetweenKills) {
            canCheckerBeKilled = false;
        }
        executeMoveBack(move);
        turnWhite = rememberIfTurnWhite;
        return canCheckerBeKilled;
    }

    public static boolean canCheckerBeKilled(Checker checker) {
        for (Checker checkerOnList: checkersPossibleToBeKilled) {
            if (checker.equals(checkerOnList)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canCheckerKill(Checker checker) {
        for (Move move: possibleKills) {
            if(move.getChecker().equals(checker)) {
                return true;
            }
        }
        for (Move move: possibleKillsByQueen) {
            if(move.getChecker().equals(checker)) {
                return true;
            }
        }
        return false;
    }
}
