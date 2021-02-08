package com.kodilla.kodillacourse.Checkers;

import static com.kodilla.kodillacourse.Checkers.Background.*;
import static com.kodilla.kodillacourse.Checkers.Board.*;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;
import static com.kodilla.kodillacourse.Checkers.CheckersApp.*;

public class MoveExecutor {

    static Move lastMove;
    static Tile previousTile;
    static Checker lastKilledChecker;
    static Checker lastKiller;
    static boolean wasQueenJustMade = false;

    public static void executeMove(Move move) {
        Checker checker = move.getChecker();
        Tile oldTile = checker.getTile();
        previousTile = oldTile;
        Tile newTile = move.getNewTile();
        MoveType moveType = move.getMoveType();

        int oldX = oldTile.getTileX();
        int oldY = oldTile.getTileY();
        int newX = newTile.getTileX();
        int newY = newTile.getTileY();

        if (moveType == MoveType.NORMAL || moveType == MoveType.QUEENMOVE) {
            checkersGroup.getChildren().remove(checker);
            oldTile.deleteChecker();
            checker.setTile(newTile);
            checker.relocate(newX * tileSize, newY * tileSize);
            newTile.setChecker(checker);
            checkersGroup.getChildren().add(checker);
            turnWhite = !turnWhite;
            lastMove = move;
        }

        if (moveType == MoveType.KILL || moveType == MoveType.QUEENKILL) {

            int dirX = (newX - oldX)/Math.abs(newX - oldX);
            int dirY = (newY - oldY)/Math.abs(newY - oldY);
            Tile tileOfKilledChecker = identifyTile(newX - dirX, newY - dirY);
            lastKilledChecker = tileOfKilledChecker.getChecker();
            checkersGroup.getChildren().remove(lastKilledChecker);
            tileOfKilledChecker.deleteChecker();

            checkersGroup.getChildren().remove(checker);
            oldTile.deleteChecker();
            checker.setTile(newTile);
            checker.relocate(newTile.getTileX() * tileSize, newTile.getTileY() * tileSize);
            newTile.setChecker(checker);
            checkersGroup.getChildren().add(checker);

            analyzeBoard();
            if (!canCheckerKill(checker)) {
                turnWhite = !turnWhite;
                isBetweenKills = false;
            } else {
                isBetweenKills = true;
                lastKiller = checker;
            }

            lastMove = move;
        }

        wasQueenJustMade = false;
        if (moveType == MoveType.NONE) {
            checker.relocate(oldX * tileSize, oldY * tileSize);
        } else if (newTile.getTileY() == 0 || newTile.getTileY() == 7) {
            if(!checker.isQueen()) {
                checker.setQueen(true);
                wasQueenJustMade = true;
            }
        }

        analyzeBoard();
        updateBackground();
        if (checkersOfType.size() == 0) {
            giveUpButton.fire();
        }

        if (modeSinglePlayer && !turnWhite && !isSimulationOngoing) {
            moveAsComputerButton.fire();
        }
    }

    public static void executeMoveBack(Move move) {
        Checker checker = move.getChecker();
        Tile currentTile = move.getChecker().getTile();
        Tile tileToMoveBackTo = previousTile;
        MoveType moveType = move.getMoveType();

        int newSoOldX = tileToMoveBackTo.getTileX();
        int newSoOldY = tileToMoveBackTo.getTileY();

        if (moveType == MoveType.NORMAL || moveType == MoveType.QUEENMOVE) {
            checkersGroup.getChildren().remove(checker);
            currentTile.deleteChecker();
            checker.setTile(tileToMoveBackTo);
            checker.relocate(newSoOldX * tileSize, newSoOldY * tileSize);
            tileToMoveBackTo.setChecker(checker);
            checkersGroup.getChildren().add(checker);
            turnWhite = !turnWhite;
        }

        if (moveType == MoveType.KILL || moveType == MoveType.QUEENKILL) {

            Tile tileOfKilledChecker = lastKilledChecker.getTile();
            tileOfKilledChecker.setChecker(lastKilledChecker);
            checkersGroup.getChildren().add(lastKilledChecker);

            checkersGroup.getChildren().remove(checker);
            currentTile.deleteChecker();
            checker.setTile(tileToMoveBackTo);
            checker.relocate(newSoOldX * tileSize, newSoOldY * tileSize);
            tileToMoveBackTo.setChecker(checker);
            checkersGroup.getChildren().add(checker);
            turnWhite = !turnWhite;
        }

        if (wasQueenJustMade) {
            checker.setQueen(false);
        }

        analyzeBoard();
        updateBackground();
        if (modeSinglePlayer && !turnWhite && !isSimulationOngoing) {
            moveAsComputerButton.fire();
        }
    }
}
