package com.lemontree.launcher.controllers;

import com.lemontree.launcher.App;
import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.layouts.TitleBar;
import com.lemontree.launcher.utils.Config;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    private final Config config = App.config;

    @FXML
    public Background background;
    @FXML
    public StackPane pane;
    @FXML
    public Label titleText;
    @FXML
    public TitleBar titleBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init node layouts
        double zoom = config.getZoom();
        background.init(config, App.class.getResource("image/backgroundBig.jpg"));
        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
    }

    public void init () {
        titleBar.init(Setting.settingStage);
    }
}
