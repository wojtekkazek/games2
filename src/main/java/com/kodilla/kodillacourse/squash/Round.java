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

    public List<Player> getPlayersInvolved() {
        List<Player> playersInvolved = new ArrayList<>();
        for (Game game: getGames()) {
            playersInvolved.add(game.getFirstPlayer());
            playersInvolved.add(game.getSecondPlayer());
        }
        return  playersInvolved;
    }

}
