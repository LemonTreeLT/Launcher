package com.lemontree.launcher.layouts;

import com.lemontree.launcher.App;
import com.lemontree.launcher.controllers.SettingController;
import com.lemontree.launcher.utils.Config;

import com.lemontree.launcher.utils.CorrespondentHelper;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Setting extends Label {
    private SettingController controller;
    private boolean isAppFocused = true;

    public static Stage settingStage;

    public Setting() {
        this.setOnMouseClicked(this::onMouseClicked);
    }

    private void onMouseClicked(MouseEvent event) {
        switchVisible();
    }

    private void switchVisible() {
        if(settingStage.isShowing()) {
            settingStage.hide();
            CorrespondentHelper.getApp().requestFocus();
        } else settingStage.show();
    }

    private Stage initStage() throws IOException {
        Stage settingPage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Setting.fxml"));
        StackPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        controller = fxmlLoader.getController();

        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(String.valueOf(App.class.getResource("css/style.css")));

        settingPage.setScene(scene);
        settingPage.setTitle("Setting");
        settingPage.initStyle(StageStyle.TRANSPARENT);
        settingPage.setAlwaysOnTop(true);
        settingPage.initOwner(CorrespondentHelper.getApp());

        settingPage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                settingPage.hide();
                if(isAppFocused) CorrespondentHelper.getApp().hide();
            }
        });

        CorrespondentHelper.getApp().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !settingPage.isShowing()) {
                CorrespondentHelper.getApp().hide();
                isAppFocused = false;
            } else if(settingStage.isShowing()) isAppFocused = false;
            else if (newValue) isAppFocused = true;
        });

        addFadeInAnimation(settingPage);
        addFadeOutAnimation(settingPage);

        return settingPage;
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

    public void init() {
        try {
            settingStage = initStage();
            controller.init();

            layout(Config.getConfig().getZoom());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void layout(double zoom) {
        this.setStyle("-fx-font-size: " + 24 * zoom + "px;");
        settingStage.setWidth(1803 * 0.28 * zoom);
        settingStage.setHeight(963 * 0.28 * zoom);
    }
}
