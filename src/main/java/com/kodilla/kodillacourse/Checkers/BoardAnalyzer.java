package com.kodilla.kodillacourse.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kodilla.kodillacourse.Checkers.Background.board;
import static com.kodilla.kodillacourse.Checkers.Board.identifyTile;
import static com.kodilla.kodillacourse.Checkers.Board.tilesToHighlight;
import static com.kodilla.kodillacourse.Checkers.GameStatus.*;
import static com.kodilla.kodillacourse.Checkers.MoveExecutor.lastKiller;

public class BoardAnalyzer {

    public static int noOfWhiteCheckers;
    public static int noOfWhiteQueens;
    public static int noOfRedCheckers;
    public static int noOfRedQueens;
    public static CheckerType winner;

    public static List<Checker> checkersOfType;

    public static List<Move> possibleMoves;
    public static List<Move> possibleMovesMakingQueen;
    public static List<Move> possibleKills;
    public static List<Move> possibleKillsMakingQueen;
    public static List<Move> possibleMovesByQueen;
    public static List<Move> possibleKillsByQueen;

    public static boolean killPossible;
    public static CheckerType correctType;

    public static void analyzeBoard() {
        possibleMoves = new ArrayList<>();
        possibleMovesMakingQueen = new ArrayList<>();
        possibleKills = new ArrayList<>();
        possibleKillsMakingQueen = new ArrayList<>();
        possibleMovesByQueen = new ArrayList<>();
        possibleKillsByQueen = new ArrayList<>();

        updateQuantities();

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
        System.out.println("is kill possible? -" + (possibleKills.size() > 0 || possibleKillsByQueen.size() > 0));

        updateHighlighting();
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

//    public static boolean didSomeoneWin() {
//        if (noOfWhiteCheckers + noOfWhiteQueens == 0) {
//            winner = CheckerType.RED;
//            return true;
//        }
//        if (noOfRedCheckers + noOfRedQueens == 0) {
//            winner = CheckerType.WHITE;
//            return true;
//        }
//        return false;
//    }

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
            int x = checker.getTile().getTileX();
            int y = checker.getTile().getTileY();

            Tile tileOfPotentialMove1 = identifyTile(x+1, y+correctType.getMoveDir());
            if (tileOfPotentialMove1 != null && !tileOfPotentialMove1.hasChecker()) {
                Move move = new Move(MoveType.NORMAL, checker, tileOfPotentialMove1);
                possibleMoves.add(move);
                if (!checker.isQueen() && tileOfPotentialMove1.getTileY() == 0 || tileOfPotentialMove1.getTileY() == 7) {
                    possibleMovesMakingQueen.add(move);
                }
            }
            Tile tileOfPotentialMove2 = identifyTile(x-1, y+correctType.getMoveDir());
            if (tileOfPotentialMove2 != null && !tileOfPotentialMove2.hasChecker()) {
                Move move = new Move(MoveType.NORMAL, checker, tileOfPotentialMove2);
                possibleMoves.add(new Move(MoveType.NORMAL, checker, tileOfPotentialMove2));
                if (!checker.isQueen() && (tileOfPotentialMove2.getTileY() == 0 || tileOfPotentialMove2.getTileY() == 7)) {
                    possibleMovesMakingQueen.add(move);
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
            int x = checker.getTile().getTileX();
            int y = checker.getTile().getTileY();

            Tile tileOfPotentialKill1 = identifyTile(x+1, y+correctType.getMoveDir());
            Tile tileToLandAfterKill1 = identifyTile(x+2, y+correctType.getMoveDir()*2);
            if (tileOfPotentialKill1 != null && tileToLandAfterKill1 != null && tileOfPotentialKill1.hasChecker()) {
                if (!checker.isQueen() && tileOfPotentialKill1.getChecker().getType() != correctType &&
                        !tileToLandAfterKill1.hasChecker()) {
                    Move move = new Move(MoveType.KILL, checker, tileToLandAfterKill1);
                    possibleKills.add(move);
                    if (tileToLandAfterKill1.getTileY() == 0 || tileToLandAfterKill1.getTileY() == 7) {
                        possibleKillsMakingQueen.add(move);
                    }
                    killPossible = true;
                }
            }

            Tile tileOfPotentialKill2 = identifyTile(x-1, y+correctType.getMoveDir());
            Tile tileToLandAfterKill2 = identifyTile(x-2, y+correctType.getMoveDir()*2);
            if (tileOfPotentialKill2 != null && tileToLandAfterKill2 != null && tileOfPotentialKill2.hasChecker()) {
                if (!checker.isQueen() && tileOfPotentialKill2.getChecker().getType() != correctType &&
                        !tileToLandAfterKill2.hasChecker()) {
                    Move move = new Move(MoveType.KILL, checker, tileToLandAfterKill2);
                    possibleKills.add(move);
                    if (tileToLandAfterKill2.getTileY() == 0 || tileToLandAfterKill2.getTileY() == 7) {
                        possibleKillsMakingQueen.add(move);
                    }
                    killPossible = true;
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

                    if (tileToBeKilled.hasChecker() && tileToBeKilled.getChecker().getType() != checker.getType()) {
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
                        if (isTheWayEmpty && !tileOnDiagonal.hasChecker()) {
                            possibleKillsByQueen.add(new Move(MoveType.QUEENKILL, checker, tileOnDiagonal));
                        }
                    }
                }
            }
        }
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
