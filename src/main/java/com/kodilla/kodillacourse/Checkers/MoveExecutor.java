package com.kodilla.kodillacourse.Checkers;

import javafx.scene.Group;
import javafx.scene.Node;

import static com.kodilla.kodillacourse.Checkers.Board.*;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;
import static com.kodilla.kodillacourse.Checkers.GameStatus.isBetweenKills;
import static com.kodilla.kodillacourse.Checkers.GameStatus.turnWhite;
import static com.kodilla.kodillacourse.Checkers.MoveVerificator.isMakingQueen;

public class MoveExecutor {

    static Checker lastKiller;

    public static void executeMove(Checker checker, Tile newTile, MoveType moveType) {

        int oldX = checker.getTile().getTileX();
        int oldY = checker.getTile().getTileY();
        int newX = newTile.getTileX();
        int newY = newTile.getTileY();

        if (moveType == MoveType.NORMAL || moveType == MoveType.QUEENMOVE) {
            checkersGroup.getChildren().remove(checker);
            checker.getTile().deleteChecker();
            checker.setTile(newTile);
            checker.relocate(newX * tileSize, newY * tileSize);
            newTile.setChecker(checker);
            checkersGroup.getChildren().add(checker);
            turnWhite = !turnWhite;
        }

        if (moveType == MoveType.KILL || moveType == MoveType.QUEENKILL) {

            int dirX = (newX - oldX)/Math.abs(newX - oldX);
            int dirY = (newY - oldY)/Math.abs(newY - oldY);
            Tile tileOfKilledChecker = identifyTile(newX - dirX, newY - dirY);
            checkersGroup.getChildren().remove(tileOfKilledChecker.getChecker());
            tileOfKilledChecker.deleteChecker();

            checkersGroup.getChildren().remove(checker);
            checker.getTile().deleteChecker();
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
        }

        if (moveType == MoveType.NONE) {
            checker.relocate(oldX * tileSize, oldY * tileSize);
        }

        if (isMakingQueen && !checker.isQueen()) {
            checker.setQueen(true);
        }

        analyzeBoard();
    }
}
