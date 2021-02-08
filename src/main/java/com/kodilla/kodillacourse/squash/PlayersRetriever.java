package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;

public class PlayersRetriever {

    List<Player> realPlayers;

    public List<Player> retrieveRealPlayers() {
        realPlayers = new ArrayList<>();
//        realPlayers.add(new Player("Jarek K."));
//        realPlayers.add(new Player("Wojtek K."));
//        realPlayers.add(new Player("Pawel K."));
//        realPlayers.add(new Player("Michal M."));
//        realPlayers.add(new Player("Maciek R."));
//        realPlayers.add(new Player("Marek G."));
//        realPlayers.add(new Player("Tomek K."));
//        realPlayers.add(new Player("Kruszek"));
        generatePlayers(12);
        return realPlayers;
    }

    public void generatePlayers(int qty) {
        for (int i=0; i<qty; i++) {
            realPlayers.add(new Player("Player" + i));
        }
    }
}
