package com.lemontree.launcher.controllers;

import com.lemontree.launcher.App;
import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.Module;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final Config config = App.config;

    @FXML
    public Background background;
    @FXML
    public VBox appList;
    @FXML
    public StackPane pane;
    @FXML
    public Setting setting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double zoom = config.getZoom();
        background.init(config, App.class.getResource("image/background.png"));
        setting.init(config);
        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
        appList.setSpacing(10 * zoom);

        AppInfo[] infos = config.getAppList();
        if(infos.length == 0) {
            AppInfo emptyInfo = new AppInfo("没有应用呢", null,
                    new Image(String.valueOf(App.class.getResource("image/fileNotFound.jpg"))),
                    null, "试着添加一点应用吧");
            appList.getChildren().add(new Module(emptyInfo, config));
        } else for(AppInfo info : infos) appList.getChildren().add(new Module(info, config));
    }
}