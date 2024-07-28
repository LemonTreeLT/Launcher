package com.lemontree.launcher.controllers;

import com.lemontree.launcher.App;
import com.lemontree.launcher.utils.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    private final Config config = App.config;

    @FXML
    public ImageView background;
    @FXML
    public StackPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initBackground();
    }

    private void initBackground() {
        double zoom = config.getZoom();
        double fitWidth = 1803 * 0.28 * zoom;
        double fitHeight = 963 * 0.28 * zoom;

        Rectangle clip = new Rectangle(fitWidth, fitHeight);
        GaussianBlur blur = new GaussianBlur();

        blur.setRadius(15);
        clip.setArcHeight(20);
        clip.setArcWidth(20);

        pane.setStyle("-fx-padding: " + 10 * zoom + "px");

        background.setImage(new Image(String.valueOf(App.class.getResource("image/backgroundBig.jpg"))));
        background.setFitHeight(fitHeight);
        background.setFitWidth(fitWidth);
        background.setClip(clip);
        background.setEffect(blur);
        // background.setVisible(false); /* debug */
    }

}
