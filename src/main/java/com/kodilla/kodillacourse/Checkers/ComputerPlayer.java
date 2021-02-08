package com.kodilla.kodillacourse.Checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.kodilla.kodillacourse.Checkers.BoardAnalyzer.*;
import static com.kodilla.kodillacourse.Checkers.CheckersApp.difficultyLevel;
import static com.kodilla.kodillacourse.Checkers.CheckersApp.isBetweenKills;
import static com.kodilla.kodillacourse.Checkers.MoveExecutor.lastKiller;

public class ComputerPlayer {

    private Random randomGenerator = new Random();
    private List<Move> allPossibleKills = new ArrayList<>();
    private List<Move> allPossibleMoves = new ArrayList<>();

    public Move findMoveAsComputer() {
        Move move = lookForMoveAsComputer();
        if (!isBetweenKills) {
            return move;
        } else if (isBetweenKills && move.getChecker().equals(lastKiller)) {
            return move;
        }
        return findMoveAsComputer();
    }

    public Move lookForMoveAsComputer() {
        findAllPossibleKills();
        findAllPossibleMoves();

        if (difficultyLevel == DifficultyLevel.VERYEASY) {
            if (possibleKillsAndGettingKilledAfter.size() > 0) {
                return possibleKillsAndGettingKilledAfter.get(randomGenerator.nextInt(possibleKillsAndGettingKilledAfter.size()));
            } else if (allPossibleKills.size() > 0) {
                return allPossibleKills.get(randomGenerator.nextInt(allPossibleKills.size()));
            } else if (possibleMovesAndGettingKilledAfter.size() > 0) {
                return possibleMovesAndGettingKilledAfter.get(randomGenerator.nextInt(possibleMovesAndGettingKilledAfter.size()));
            }
            return allPossibleMoves.get(randomGenerator.nextInt(allPossibleMoves.size()));
        }

        if (difficultyLevel == DifficultyLevel.EASY) {
            if (allPossibleKills.size() > 0) {
                return allPossibleKills.get(randomGenerator.nextInt(allPossibleKills.size()));
            }
            return allPossibleMoves.get(randomGenerator.nextInt(allPossibleMoves.size()));
        }

        if (difficultyLevel == DifficultyLevel.MEDIUM) {
            if (possibleKillsKillingQueenAndMakingQueen.size() > 0) {
                return possibleKillsKillingQueenAndMakingQueen.get(randomGenerator.nextInt(possibleKillsKillingQueenAndMakingQueen.size()));
            } else if (possibleKillsMakingQueen.size() > 0) {
                return possibleKillsMakingQueen.get(randomGenerator.nextInt(possibleKillsMakingQueen.size()));
            } else if (possibleKillsKillingQueen.size() > 0) {
                return possibleKillsKillingQueen.get(randomGenerator.nextInt(possibleKillsKillingQueen.size()));
            } else if (allPossibleKills.size() > 0) {
                return allPossibleKills.get(randomGenerator.nextInt(allPossibleKills.size()));
            } else if (possibleMovesMakingQueen.size() > 0) {
                return possibleMovesMakingQueen.get(randomGenerator.nextInt(possibleMovesMakingQueen.size()));
            }
            return allPossibleMoves.get(randomGenerator.nextInt(allPossibleMoves.size()));
        }

        if (difficultyLevel == DifficultyLevel.HARD) {
            if (possibleKillsKillingQueenAndMakingQueen.size() > 0) {
                return possibleKillsKillingQueenAndMakingQueen.get(randomGenerator.nextInt(possibleKillsKillingQueenAndMakingQueen.size()));
            } else if (possibleKillsMakingQueen.size() > 0) {
                return possibleKillsMakingQueen.get(randomGenerator.nextInt(possibleKillsMakingQueen.size()));
            } else if (possibleKillsKillingQueenAndNotGettingKilledAfter.size() > 0) {
                return possibleKillsKillingQueenAndNotGettingKilledAfter.get(randomGenerator.nextInt(possibleKillsKillingQueenAndNotGettingKilledAfter.size()));
            } else if (possibleKillsKillingQueen.size() > 0) {
                return possibleKillsKillingQueen.get(randomGenerator.nextInt(possibleKillsKillingQueen.size()));
            } else if (possibleKillsAndNotGettingKilledAfter.size() > 0) {
                return possibleKillsAndNotGettingKilledAfter.get(randomGenerator.nextInt(possibleKillsAndNotGettingKilledAfter.size()));
            } else if (allPossibleKills.size() > 0) {
                return allPossibleKills.get(randomGenerator.nextInt(allPossibleKills.size()));
            } else if (possibleMovesMakingQueen.size() > 0) {
                return possibleMovesMakingQueen.get(randomGenerator.nextInt(possibleMovesMakingQueen.size()));
            } else if (possibleMovesAndNotGettingKilledAfter.size() > 0) {
                return possibleMovesAndNotGettingKilledAfter.get(randomGenerator.nextInt(possibleMovesAndNotGettingKilledAfter.size()));
            }
            return allPossibleMoves.get(randomGenerator.nextInt(allPossibleMoves.size()));
        }
        return null;
    }

    public void findAllPossibleKills() {
        allPossibleKills.clear();
        allPossibleKills.addAll(possibleKills);
        allPossibleKills.addAll(possibleKillsByQueen);
    }

    public void findAllPossibleMoves() {
        allPossibleMoves.clear();
        allPossibleMoves.addAll(possibleMoves);
        allPossibleMoves.addAll(possibleMovesByQueen);
    }
}
