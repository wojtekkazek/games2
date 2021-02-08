package com.kodilla.kodillacourse.squash;

import java.util.Objects;

public class Game {
     private Player firstPlayer;
     private Player secondPlayer;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public String toString() {
        return firstPlayer + " - " + secondPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return (Objects.equals(firstPlayer, game.firstPlayer) &&
                Objects.equals(secondPlayer, game.secondPlayer))
                || (Objects.equals(firstPlayer, game.secondPlayer) &&
                Objects.equals(secondPlayer, game.firstPlayer));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayer, secondPlayer);
    }
}
