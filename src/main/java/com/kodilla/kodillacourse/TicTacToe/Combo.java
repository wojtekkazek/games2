package com.kodilla.kodillacourse.TicTacToe;

import java.util.Arrays;

public class Combo {

    private Tile[] tiles;

    public Combo(Tile... tiles) {
        this.tiles = tiles;
    }

    public void addTile(Tile tile) {
        int currentSize = tiles.length;
        Tile[] newTiles = new Tile[currentSize+1];
        for (int i=0; i< currentSize; i++) {
            newTiles[i] = tiles[i];
        }
        newTiles[currentSize] = tile;
        tiles = newTiles;
    }

    public boolean isComplete() {
        if (tiles[0].getValue().isEmpty()) {
            return false;
        }

        boolean isComplete = true;
        for (int i=1; i<tiles.length; i++)
            isComplete = isComplete && tiles[0].getValue().equals(tiles[i].getValue());

        return isComplete;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
