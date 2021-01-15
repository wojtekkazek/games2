package com.kodilla.kodillacourse.Checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

import static com.kodilla.kodillacourse.Checkers.Board.identifyTileByCoordinates;
import static com.kodilla.kodillacourse.Checkers.Board.tileSize;
import static com.kodilla.kodillacourse.Checkers.MoveExecutor.executeMove;
import static com.kodilla.kodillacourse.Checkers.MoveVerificator.verifyMove;

public class Checker extends StackPane {

    private int id;
    private Tile tile;
    private CheckerType type;
    private boolean isQueen;
//    private double pressedMouseCX;
//    private double pressedMouseCY;

    Circle circle = new Circle(tileSize * 0.3);
    Circle queenCircle = new Circle(tileSize * 0.2);

    public Checker(int id, CheckerType type, boolean isQueen, Tile tile) {
        this.id = id;
        this.type = type;
        this.isQueen = isQueen;
        this.tile = tile;

        circle.setFill(type.getCheckerColor());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(tileSize * 0.03);
        circle.setTranslateX(10);
        circle.setTranslateY(10);

        queenCircle.setFill(Color.BLACK);
        queenCircle.setTranslateX(10);
        queenCircle.setTranslateY(10);

        getChildren().addAll(circle);

        setOnMouseDragged(e -> {
            relocate(e.getSceneX()-tileSize/2, e.getSceneY()-tileSize/2);
        });

        setOnMouseReleased(e -> {
            move(e.getSceneX(), e.getSceneY());
        });

    }

    public void move(double newCX, double newCY) {
        Tile newTile = identifyTileByCoordinates(newCX, newCY);
        MoveType moveType = verifyMove(this, newTile, newCX, newCY);
        executeMove(this, newTile, moveType);
    }

    public Tile getTile() {
        return tile;
    }

    public CheckerType getType() {
        return type;
    }

    public boolean isQueen() {
        return isQueen;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setQueen(boolean queen) {
        isQueen = queen;
        getChildren().addAll(queenCircle);
    }

    @Override
    public String toString() {
        return "Checker{" +
                "id=" + id +
                ", tileX=" + tile.getTileX() +
                ", tileY=" + tile.getTileY() +
                ", type=" + type +
                ", isqueen=" + isQueen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checker checker = (Checker) o;
        return id == checker.id &&
                isQueen == checker.isQueen &&
                Objects.equals(tile, checker.tile) &&
                type == checker.type &&
                Objects.equals(circle, checker.circle) &&
                Objects.equals(queenCircle, checker.queenCircle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tile, type, isQueen, circle, queenCircle);
    }
}