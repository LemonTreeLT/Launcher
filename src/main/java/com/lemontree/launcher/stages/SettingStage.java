package com.lemontree.launcher.stages;

import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class SettingStage extends Stage{
    private boolean isAppFocused = true;

    public SettingStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CorrespondentHelper.getResource("setting.fxml"));
        StackPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);

        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(String.valueOf(CorrespondentHelper.getResource("css/style.css")));

        this.setScene(scene);
        this.setTitle("Setting");
        this.initStyle(StageStyle.TRANSPARENT);
        this.setAlwaysOnTop(true);
        layout(Config.getConfig().getZoom());

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                this.hide();
                if(isAppFocused) CorrespondentHelper.getApp().hide();
            }
        });

        CorrespondentHelper.getApp().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !this.isShowing()) {
                CorrespondentHelper.getApp().hide();
                isAppFocused = false;
            } else if(this.isShowing()) isAppFocused = false;
            else if (newValue) isAppFocused = true;
        });

        addFadeInAnimation(this);
        addFadeOutAnimation(this);
    }

    private void layout(double zoom) {
        this.setWidth(1803 * 0.28 * zoom);
        this.setHeight(963 * 0.28 * zoom);
    }

    private void addFadeInAnimation(Stage stage) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(100));
        fadeIn.setNode(stage.getScene().getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        stage.setOnShowing(event -> fadeIn.play());
    }

    private void addFadeOutAnimation(Stage stage) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(100));
        fadeIn.setNode(stage.getScene().getRoot());
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        stage.setOnHidden(event -> fadeIn.play());
    }
}
