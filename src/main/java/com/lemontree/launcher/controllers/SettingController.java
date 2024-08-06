package com.lemontree.launcher.controllers;

import com.lemontree.launcher.layouts.AddApp;
import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.layouts.TitleBar;
import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    public Background background;
    public StackPane pane;
    public Label titleText;
    public TitleBar titleBar;
    public HBox setting_content;
    public VBox appList;
    public AddApp addAppButtom;
    public VBox appInfo;
    public VBox options;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init node layouts
        double zoom = Config.getConfig().getZoom();
        background.init(CorrespondentHelper.getResource("image/backgroundBig.jpg"));
        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
    }

    public void init () {
        titleBar.init(Setting.settingStage);
        addAppButtom.init();
    }
}
