package com.kodilla.kodillacourse.TicTacToe;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int size = 5;

    private Tile[][] board = new Tile[size][size];
    private List<Combo> combos = new ArrayList<>();

    private Pane root = new Pane();

//    public Board(int size) {
//        this.size = size;
//    }

    public Parent createContent() {
        root.setPrefSize((200*size)+2.5*(size-1), (200*size)+2.5*(size-1));

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);

                board[j][i] = tile;

            }
        }

        // horizontal
        for (int y = 0; y < size; y++) {
            Combo combo = new Combo();
            for (int i=0; i<size; i++) {
                combo.addTile(board[i][y]);
            }
            combos.add(combo);
        }

        // vertical
        for (int x = 0; x < size; x++) {
            Combo combo = new Combo();
            for (int i=0; i<size; i++) {
                combo.addTile(board[x][i]);
            }
            combos.add(combo);
        }

        //diagonal
        Combo combo = new Combo();
        for (int i=0; i<size; i++) {
            combo.addTile(board[i][i]);
        }
        combos.add(combo);

      combo = new Combo();
        for (int i=0; i<size; i++) {
            combo.addTile(board[size-i-1][i]);
        }
        System.out.println(combo);
        combos.add(combo);

        return root;
    }

    public List<Combo> getCombos() {
        return combos;
    }

    public Pane getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
