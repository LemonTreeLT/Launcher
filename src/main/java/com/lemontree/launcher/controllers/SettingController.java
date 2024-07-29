package com.lemontree.launcher.controllers;

import com.lemontree.launcher.App;
import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.layouts.TitleBar;
import com.lemontree.launcher.utils.Config;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    public Background background;
    @FXML
    public StackPane pane;
    @FXML
    public Label titleText;
    @FXML
    public TitleBar titleBar;
    @FXML
    public HBox setting_content;
    @FXML
    public VBox appList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init node layouts
        double zoom = Config.getConfig().getZoom();
        background.init(App.class.getResource("image/backgroundBig.jpg"));
        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
    }

    public void init () {
        titleBar.init(Setting.settingStage);
    }
}
