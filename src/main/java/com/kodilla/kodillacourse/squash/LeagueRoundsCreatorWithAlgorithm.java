package com.kodilla.kodillacourse.squash;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LeagueRoundsCreatorWithAlgorithm {

    public League createLeagueWithAlgorithm(String leagueName, List<Player> players) {
        League league = new League(leagueName, players.size());

        for (int i=0; i<players.size(); i++) {
            for (int j=0; j<players.size(); j++) {
                int k;
                int roundNo;
                if (j == players.size()-1) {
                    k=i;
                } else {
                    k=j;
                }
                if(i == 0 && j == players.size()-1) {
                    roundNo = players.size()-2;
                } else if (i+k <= players.size()-1) {
                    roundNo = i+k-1;
                } else {
                    roundNo = i+k-players.size();
                }
                if(i<j) {
                    Game game = new Game(players.get(i), players.get(j));
                    league.getRounds().get(roundNo).addGame(game);
                }
            }

        }
        return league;
    }

}
