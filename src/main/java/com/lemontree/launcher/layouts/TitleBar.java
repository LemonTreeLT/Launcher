package com.lemontree.launcher.layouts;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public void init(Stage stage) {
        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
