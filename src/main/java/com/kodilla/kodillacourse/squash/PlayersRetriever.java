package com.kodilla.kodillacourse.squash;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayersRetriever {

    public List<Player> makeListOfPlayers(int qty) {
        List<Player> players = retrieveRealPlayers(qty);
        if (!(players.size() % 2 == 0)) {
            players.add(new Player("PAUSE"));
        }
        return randomlyMixPlayersList(players);
    }

    public List<Player> retrieveRealPlayers(int qty) {
        List<Player> realPlayers = new ArrayList<>();
//        realPlayers.add(new Player("Jarek K."));
//        realPlayers.add(new Player("Wojtek K."));
//        realPlayers.add(new Player("Pawel K."));
//        realPlayers.add(new Player("Michal M."));
//        realPlayers.add(new Player("Maciek R."));
//        realPlayers.add(new Player("Marek G."));
//        realPlayers.add(new Player("Tomek K."));
//        realPlayers.add(new Player("Kruszek"));
        realPlayers = generateAndAddExtraPlayers(realPlayers, qty - realPlayers.size());
        return realPlayers;
    }

    public List<Player> generateAndAddExtraPlayers(List<Player> players, int qty) {
        for (int i=0; i<qty; i++) {
            players.add(new Player("Player" + i));
        }
        return players;
    }

    public List<Player> randomlyMixPlayersList(List<Player> players) {
        List<Player> randomlyMixedPlayers = new ArrayList<>();
        Random randomGenerator = new Random();
        int playersQty = players.size();
        for (int i=0; i<playersQty; i++) {
            int j = randomGenerator.nextInt(players.size());
            randomlyMixedPlayers.add(players.get(j));
            players.remove(j);
        }
        return randomlyMixedPlayers;
    }
}
