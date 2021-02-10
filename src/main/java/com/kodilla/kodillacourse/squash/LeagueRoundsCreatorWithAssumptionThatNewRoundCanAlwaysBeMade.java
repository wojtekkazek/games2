package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
The assumption is most likely wrong. Method sometimes does and sometimes doesn't work - gets lost in the loop.
Not likely to work with big numbers.
Highest achieved no. of players, after several tries was 100 (more players = less probable to give results)
*/

public class LeagueRoundsCreatorWithAssumptionThatNewRoundCanAlwaysBeMade {

    List<Game> remainingGames;
    int noOfGamesInRound;

    public League createLeagueAssumingNewRoundCanBeAlwaysDone(String leagueName, List<Player> players) {
        League league = new League(leagueName, players.size());
        List<Game> allGames = listAllGames(players);
        remainingGames = new ArrayList<>(allGames);
        noOfGamesInRound = league.getNoOfGamesInRound();

        for(Round round: league.getRounds()) {
            arrangeRound(round);
        }

        return league;
    }

    public void arrangeRound(Round round) {
        List<Game> remainingGamesToTryInThisRound = new ArrayList<>(remainingGames);
        for (int i=0; i < noOfGamesInRound; i++) {
            if(remainingGamesToTryInThisRound.isEmpty()) {
                remainingGamesToTryInThisRound = new ArrayList<>(remainingGames);
                i=0;
                round.emptyList();
            }
            Game game = drawGameFromList(remainingGamesToTryInThisRound);
            round.addGame(game);
            remainingGamesToTryInThisRound = removeFromTheListGamesInvolvingPlayersOfGame(remainingGamesToTryInThisRound, game);
        }
        for (Game game: round.getGames()) {
            remainingGames.remove(game);
        }
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
