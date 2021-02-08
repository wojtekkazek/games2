package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SquashDraw {

    PlayersRetriever playersRetriever = new PlayersRetriever();
    List<Player> players = new ArrayList<>();
    List<Round> rounds;
    List<Game> allGames;
    List<Game> remainingGames;
    int noOfRounds;
    int noOfGamesInRound;

    public void getListOfPlayersAndSetQuantities() {
        players = playersRetriever.retrieveRealPlayers();
        if (!(players.size() %2 == 0)) {
            Player pause = new Player("PAUSE");
            players.add(pause);
        }
        noOfRounds = players.size() - 1;
        noOfGamesInRound = players.size() / 2;
        System.out.println(noOfRounds);
    }

    public void initiateRounds() {
        rounds = new ArrayList<>();
        for(int i=0; i<noOfRounds; i++) {
            rounds.add(new Round(i+1, noOfGamesInRound));
        }
    }

    public Game drawGameFromList(List<Game> games) {
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(games.size());
        return games.get(i);
    }

    public void listAllGames() {
        allGames = new ArrayList<>();
        List<Player> remainingPlayersToMakeGamesWith = new ArrayList<>(players);
        for (int i=0; i<players.size(); i++) {
            Player player = players.get(i);
            remainingPlayersToMakeGamesWith.remove(player);
            for (int j=0; j<remainingPlayersToMakeGamesWith.size(); j++) {
                allGames.add(new Game(player, remainingPlayersToMakeGamesWith.get(j)));
            }
        }
        remainingGames = new ArrayList<>(allGames);
    }

    public boolean arrangeRound(Round round) {
        List<Game> remainingGamesToTryInThisRound = new ArrayList<>(remainingGames);
        int noOfGamesLeftBeforeArrangingThisRound = remainingGames.size();
        int noOfTries = 0;
        for (int i=0; i < noOfGamesInRound; i++) {
            noOfTries++;
            if(noOfTries > noOfGamesLeftBeforeArrangingThisRound) {
                return false;
            }
            Game game = drawGameFromList(remainingGamesToTryInThisRound);
            remainingGamesToTryInThisRound.remove(game);
            if (round.getPlayersInvolved().contains(game.getFirstPlayer()) ||
                    round.getPlayersInvolved().contains(game.getSecondPlayer())) {
                i--;
            } else {
                round.addGame(game);
                remainingGames.remove(game);
            }
        }
        if (round.getGames().size() != noOfGamesInRound) {
            return false;
        }
//        System.out.println(round.getGames().size());
        return true;
    }

    public void arrangeRounds() {
        getListOfPlayersAndSetQuantities();
        initiateRounds();
        listAllGames();

        boolean roundOk = true;
        for(Round round: rounds) {
            if(roundOk) {
                roundOk = arrangeRound(round);
            }
//            printRound(round);
        }
        if (!roundOk) {
            arrangeRounds();
        }
    }

    public void printRounds() {
        int gameNo = 0;
        for (Round round: rounds) {
            System.out.println("Round " + round.getRoundNo() + ":");
            for (Game game: round.getGames()) {
                gameNo++;
                System.out.println(gameNo + ". " + game);
            }
            System.out.println("");
        }
    }

    public void printRound(Round round) {
        System.out.println("Round " + round.getRoundNo() + ":");
        for (Game game: round.getGames()) {
            System.out.println(game);
        }
        System.out.println("");
    }
}
