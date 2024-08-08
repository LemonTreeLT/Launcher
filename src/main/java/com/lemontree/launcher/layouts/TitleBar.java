package com.lemontree.launcher.layouts;

import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleBar extends HBox implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Window window = getScene().getWindow();

        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });
    }
}
