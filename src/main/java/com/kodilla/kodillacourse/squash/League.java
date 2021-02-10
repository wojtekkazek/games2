package com.kodilla.kodillacourse.squash;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class League {

    String name;
    int noOfRounds;
    int noOfGamesInRound;
    List<Round> rounds;

    public League(String name, int noOfPlayers) {
        this.name = name;
        noOfRounds = noOfPlayers - 1;
        noOfGamesInRound = noOfPlayers/2;
        rounds = new ArrayList<>();
        for(int i=0; i<noOfRounds; i++) {
            rounds.add(new Round(i+1, noOfGamesInRound));
        }
    }

    public void printRounds() {
        System.out.println("Rounds of league " + '"' + name + '"' + ":");
        System.out.println();
        int gameNo = 0;
        for (Round round: rounds) {
            System.out.println("Round " + round.getRoundNo() + ":");
            for (Game game: round.getGames()) {
                gameNo++;
                System.out.println(gameNo + ". " + game);
            }
            System.out.println();
        }
    }

    public String getName() {
        return name;
    }

    public int getNoOfRounds() {
        return noOfRounds;
    }

    public int getNoOfGamesInRound() {
        return noOfGamesInRound;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
