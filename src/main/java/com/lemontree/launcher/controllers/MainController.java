package com.lemontree.launcher.controllers;

import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.Module;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Background background;
    public VBox appList;
    public StackPane pane;
    public Setting setting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.init(CorrespondentHelper.getResource("image/background.png"));

        layout(Config.getConfig().getZoom());
        Config.getConfig().addOnZoomChangedListener(this::layout);

        AppInfo[] infos = Config.getConfig().getAppList();
        if(infos.length == 0) {
            AppInfo emptyInfo = new AppInfo("没有应用呢", null,
                    new Image(String.valueOf(CorrespondentHelper.getResource("image/fileNotFound.jpg"))),
                    null, "试着添加一点应用吧");
            appList.getChildren().add(new Module(emptyInfo));
        } else for(AppInfo info : infos) appList.getChildren().add(new Module(info));
    }

    private void layout(double zoom) {
        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
        appList.setSpacing(10 * zoom);
    }
}