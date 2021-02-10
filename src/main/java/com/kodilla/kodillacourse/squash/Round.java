package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;

public class Round {
    int roundNo;
    int noOfGames;
    private List<Game> games = new ArrayList<>();

    public Round(int roundNo, int noOfGames) {
        this.roundNo = roundNo;
        this.noOfGames = noOfGames;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public int getRoundNo() {
        return roundNo;
    }

    public List<Game> getGames() {
        return games;
    }

    public void emptyList() {
        games = new ArrayList<>();
    }

}
