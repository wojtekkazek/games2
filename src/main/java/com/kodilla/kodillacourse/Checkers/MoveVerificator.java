package com.kodilla.kodillacourse.Checkers;

import javafx.scene.paint.Color;

import static com.kodilla.kodillacourse.Checkers.Board.*;
import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;
import static com.kodilla.kodillacourse.Checkers.GameStatus.*;
import static com.kodilla.kodillacourse.Checkers.MoveExecutor.lastKiller;

public class MoveVerificator {

    private static boolean correctTurn;
    public static MoveType moveType;
    public static boolean isMakingQueen;

    public static MoveType verifyMove(Checker checker, Tile tile, double newCX, double newCY) {
        isMakingQueen = false;
        moveType = MoveType.NONE;
        isItCorrectTurn(checker);
        if (gameOn && isDestinationWithinBoard(newCX, newCY) && correctTurn && tile.getColor() == Color.GREEN && !tile.hasChecker()) {
                defineMoveType(checker, tile);
                if (moveType != MoveType.NONE) {
                    if (tile.getTileY() == 0 || tile.getTileY() == 7) {
                        isMakingQueen = true;
                    }
                }
        }
        System.out.println("move was verified");
        return moveType;
    }

    public static boolean isDestinationWithinBoard(double cX, double cY) {
        if (cX > 0 && cX < tileSize * width && cY > 0 && cY < tileSize * height) {
            return true;
        }
        return false;
    }

    public static void isItCorrectTurn(Checker checker) {
        if (turnWhite && checker.getType() == CheckerType.WHITE
                || !turnWhite && checker.getType() == CheckerType.RED) {
            correctTurn = true;
        } else {
            correctTurn = false;
        }
    }

    public static void defineMoveType(Checker checker, Tile tile) {

        Move move = new Move(MoveType.NORMAL, checker, tile);
        for (Move possibleMove: possibleMoves) {
            if (move.equals(possibleMove) && possibleKills.size() == 0 && possibleKillsByQueen.size() == 0) {
                moveType = MoveType.NORMAL;
            }
        }

        Move moveByQueen = new Move(MoveType.QUEENMOVE, checker, tile);
        for (Move possibleMoveByQueen: possibleMovesByQueen) {
            if (moveByQueen.equals(possibleMoveByQueen) && possibleKills.size() == 0 && possibleKillsByQueen.size() == 0) {
                moveType = MoveType.QUEENMOVE;
            }
        }

        Move kill = new Move(MoveType.KILL, checker, tile);
        for (Move possibleKill: possibleKills) {
            if (kill.equals(possibleKill) && (!isBetweenKills || checker.equals(lastKiller))) {
                moveType = MoveType.KILL;
            }
        }

        Move killByQueen = new Move(MoveType.QUEENKILL, checker, tile);
        for (Move possibleKillByQueen: possibleKillsByQueen) {
            if (killByQueen.equals(possibleKillByQueen) && (!isBetweenKills || checker.equals(lastKiller))) {
                moveType = MoveType.QUEENKILL;
            }
        }
    }
}
