package com.lemontree.launcher.layouts;

import com.lemontree.launcher.App;
import com.lemontree.launcher.controllers.SettingController;
import com.lemontree.launcher.utils.Config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Setting extends Label {
    private double zoom;
    private SettingController controller;

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
            App.stage.requestFocus();
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
        settingPage.setWidth(1803 * 0.28 * zoom);
        settingPage.setHeight(963 * 0.28 * zoom);
        settingPage.setAlwaysOnTop(true);

        settingPage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                settingPage.hide();
                if(App.isFoucsed) App.stage.hide();
            }
        });

        App.stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !settingPage.isShowing()) {
                App.stage.hide();
                App.isFoucsed = false;
            } else if(settingStage.isShowing()) App.isFoucsed = false;
            else if (newValue) App.isFoucsed = true;
        });

        return settingPage;
    }

    public void init() {
        Config config = Config.getConfig();
        this.zoom = config.getZoom();

        try {
            settingStage = initStage();
            controller.init();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        this.setStyle("-fx-font-size: " + 24 * zoom + "px;");
    }
}
