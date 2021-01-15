package com.kodilla.kodillacourse.TicTacToe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class BoardDecorator {

    public void playWinAnimation(Board board, Combo combo) {
        Line line = new Line();
        line.setStartX(combo.getTiles()[0].getCentreX());
        line.setStartY(combo.getTiles()[0].getCentreY());
        line.setEndX(combo.getTiles()[0].getCentreX());
        line.setEndY(combo.getTiles()[0].getCentreY());
        line.setStroke(Color.RED);
        line.setStrokeWidth(5);

        board.getRoot().getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.getTiles()[board.getSize()-1].getCentreX()),
                new KeyValue(line.endYProperty(), combo.getTiles()[board.getSize()-1].getCentreY())));
        timeline.play();
    }
}
