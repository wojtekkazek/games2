package com.kodilla.kodillacourse.Checkers;

import javafx.scene.Group;

import static com.kodilla.kodillacourse.Checkers.Background.board;

public class Board {

    public static final int tileSize = 50;
    public static final int width = 8;
    public static final int height = 8;

    private static Tile[][] boardOfTiles = new Tile [width][height];
    public static Group tilesGroup = new Group();
    public static Group checkersGroup = new Group();
    public static Group tilesToHighlight = new Group();

    public Board() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = new Tile(tileSize, (x + y) % 2 == 0, x, y);
                boardOfTiles[x][y] = tile;
                tilesGroup.getChildren().add(tile);
            }
        }
    }

    public void setCheckers() {
        int id = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = boardOfTiles[x][y];
                Checker checker = null;

                if (y <= 2 && (x+y)%2 != 0) {
                    checker = new Checker(id, CheckerType.RED, false, tile);
                    checker.relocate(x*tileSize, y*tileSize);
                }

                if (y >= 5 && (x+y)%2 != 0) {
                    checker = new Checker(id, CheckerType.WHITE, false, tile);
                    checker.relocate(x*tileSize, y*tileSize);
                }

                if (checker != null) {
                    id++;
                    tile.setChecker(checker);
                    checkersGroup.getChildren().add(checker);
                }
            }
        }
    }

    public void resetCheckers() {
        checkersGroup.getChildren().clear();
        tilesToHighlight.getChildren().clear();
        for (Tile[] tileRow: board.getBoard()) {
            for(Tile tile: tileRow) {
                tile.deleteChecker();
            }
        }
        setCheckers();
    }

    public static Tile identifyTileByCoordinates (double cX, double cY) {
        for(Tile[] tileRow: boardOfTiles) {
            for(Tile tile: tileRow) {
                if (cX > tile.getTileX()*tileSize && cX < (tile.getTileX()+1)*tileSize &&
                        cY > tile.getTileY()*tileSize && cY < (tile.getTileY()+1)*tileSize) {
//                    System.out.println(tile);
                    return tile;
                }
            }
        }
        return null;
    }

    public static Tile identifyTile (int x, int y) {
        for(Tile[] tileRow: boardOfTiles) {
            for(Tile tile: tileRow) {
                if (x == tile.getTileX() && y == tile.getTileY()) {
//                    System.out.println(tile);
                    return tile;
                }
            }
        }
        return null;
    }

    public Group getTilesGroup() {
        return tilesGroup;
    }

    public Tile[][] getBoard() {
        return boardOfTiles;
    }

    public Group getCheckersGroup() {
        return checkersGroup;
    }

    public Group getTilesToHighlight() {
        return tilesToHighlight;
    }
}
