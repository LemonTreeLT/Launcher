package com.lemontree.launcher.controllers;

import com.lemontree.launcher.layouts.AddApp;
import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.ReduceApp;
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
    public ReduceApp reduceAppButtom;
    public StackPane pane;
    public Label titleText;
    public TitleBar titleBar;
    public AddApp addAppButtom;
    public HBox settingContent;
    public VBox appList;
    public VBox appInfo;
    public VBox options;


    private double zoom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.zoom = Config.getConfig().getZoom();

        background.init(CorrespondentHelper.getResource("image/backgroundBig.jpg"));
        addAppButtom.init();
        reduceAppButtom.init();

        this.layout();

        Config.getConfig().addOnZoomChangedListener(this::onZoomChanged);
    }

    private void onZoomChanged(double zoom) {
        this.zoom = zoom;
        this.pane.setStyle("-fx-padding: " + 10 * zoom + "px");
        this.layout();
    }

    private void layout() {
        // Add a listener to the width property of the HBox to dynamically calculate the layout
        settingContent.widthProperty().addListener((observable, oldValue, newValue) -> {
            double totalWidth = newValue.doubleValue();
            double spacing = settingContent.getSpacing();
            double availableWidth = (totalWidth - spacing * 2 - 15) * zoom; // Since there are 3 VBoxes and 2 spacings between them

            double appListWidth = availableWidth * 0.4;
            double appInfoWidth = availableWidth * 0.3;
            double optionsWidth = availableWidth * 0.3;

            appList.setPrefWidth(appListWidth);
            appInfo.setPrefWidth(appInfoWidth);
            options.setPrefWidth(optionsWidth);

            appList.setMinWidth(appListWidth);
            appList.setMaxWidth(appListWidth);
            appInfo.setMinWidth(appInfoWidth);
            appInfo.setMaxWidth(appInfoWidth);
            options.setMinWidth(optionsWidth);
            options.setMaxWidth(optionsWidth);
        });
    }
}
