package com.kodilla.kodillacourse.squash;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        PlayersRetriever playersRetriever = new PlayersRetriever();
        List<Player> players = playersRetriever.makeListOfPlayers(50);

        long startTime = System.currentTimeMillis();
        LeagueRoundsCreatorWithAlgorithm creatorWithAlgorithm = new LeagueRoundsCreatorWithAlgorithm();
        League league1 = creatorWithAlgorithm.createLeagueWithAlgorithm("Squash", players);
        long stopTime = System.currentTimeMillis();
        System.out.println("League creating time with algorithm: " + (stopTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        LeagueRoundsCreatorWithAssumptionThatNewRoundCanAlwaysBeMade creator = new LeagueRoundsCreatorWithAssumptionThatNewRoundCanAlwaysBeMade();
        League league2 = creator.createLeagueAssumingNewRoundCanBeAlwaysDone("Squash", players);
        stopTime = System.currentTimeMillis();
        System.out.println("League creating time with wrong assumption: " + (stopTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        LeagueRoundsCreatorWithDummyRepetition dummyCreator = new LeagueRoundsCreatorWithDummyRepetition();
        League league3 = dummyCreator.createLeagueWithRepetitions("Squash", players);
        stopTime = System.currentTimeMillis();
        System.out.println("League creating time with dummy repetitions: " + (stopTime - startTime) + " ms");

        System.out.println();
        league1.printRounds();
    }
}
