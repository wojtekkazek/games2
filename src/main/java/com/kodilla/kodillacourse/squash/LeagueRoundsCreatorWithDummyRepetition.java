package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Works well with small numbers - say up to around 50 players..
Should be working with any number but takes unreasonable amount of time.
Interesting question is, what is the optimal number of tries of creating a new Round until it gives up and starts from scratch.
Also, is the optimal number constant or dependable on no. of players.
Currently, it's 20 tries - line 57. Question of probability.
*/

public class LeagueRoundsCreatorWithDummyRepetition {

    private List<Game> remainingGames;
    private int noOfGamesInRound;

    public League createLeagueWithRepetitions(String leagueName, List<Player> players) {
        League league = new League(leagueName, players.size());
        List<Game> allGames = listAllGames(players);
        remainingGames = new ArrayList<>(allGames);
        noOfGamesInRound = league.getNoOfGamesInRound();

        while (!tryToCreateLeague(league)) {
            league = new League(leagueName, players.size());
            remainingGames = new ArrayList<>(allGames);
//            tryToCreateLeague(league);
        }
        return league;
    }

    public boolean tryToCreateLeague(League league) {

        for(Round round: league.getRounds()) {
            boolean roundOk = arrangeRound(round);
            if (!roundOk) {
                return false;
            }
        }
        return true;
    }

    public boolean arrangeRound(Round round) {
        List<Game> remainingGamesToTryInThisRound = new ArrayList<>(remainingGames);
        int noOfTries = 0;
        for (int i=0; i < noOfGamesInRound; i++) {
            if(remainingGamesToTryInThisRound.isEmpty()) {
                remainingGamesToTryInThisRound = new ArrayList<>(remainingGames);
                if(remainingGames.isEmpty()) {
                    System.out.println("attention");
                }
                i=0;
                round.emptyList();
                noOfTries++;
                if (noOfTries > noOfGamesInRound*20) {
                    return false;
                }
            }
            Game game = drawGameFromList(remainingGamesToTryInThisRound);
            round.addGame(game);
            remainingGamesToTryInThisRound = removeFromTheListGamesInvolvingPlayersOfGame(remainingGamesToTryInThisRound, game);
        }
        for (Game game: round.getGames()) {
            remainingGames.remove(game);
        }
        return true;
    }

    public List<Game> removeFromTheListGamesInvolvingPlayersOfGame (List<Game> list, Game game) {
        Player involvedPlayer1 = game.getFirstPlayer();
        Player involvedPlayer2 = game.getSecondPlayer();
        List<Game> gamesToRemainOnList = new ArrayList<>();
        for (Game gameFromList: list) {
            if (!(gameFromList.getFirstPlayer().equals(involvedPlayer1) ||
                    gameFromList.getFirstPlayer().equals(involvedPlayer2) ||
                    gameFromList.getSecondPlayer().equals(involvedPlayer1) ||
                    gameFromList.getSecondPlayer().equals(involvedPlayer2))) {
                gamesToRemainOnList.add(gameFromList);
            }
        }
        return gamesToRemainOnList;
    }

    public Game drawGameFromList(List<Game> games) {
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(games.size());
        return games.get(i);
    }

    public List<Game> listAllGames(List<Player> players) {
        List<Game> allGames = new ArrayList<>();
        List<Player> remainingPlayersToMakeGamesWith = new ArrayList<>(players);
        for (int i=0; i<players.size(); i++) {
            Player player = players.get(i);
            remainingPlayersToMakeGamesWith.remove(player);
            for (int j=0; j<remainingPlayersToMakeGamesWith.size(); j++) {
                allGames.add(new Game(player, remainingPlayersToMakeGamesWith.get(j)));
            }
        }
        return allGames;
    }

}
