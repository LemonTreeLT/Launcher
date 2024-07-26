package com.lemontree.launcher.controllers;

import com.lemontree.launcher.App;
import com.lemontree.launcher.layouts.Module;
import com.lemontree.launcher.layouts.Setting;
import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final Config config = App.config;

    @FXML
    public ImageView background;
    @FXML
    public VBox appList;
    @FXML
    public StackPane pane;
    @FXML
    public Setting setting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initBackground();

        AppInfo[] infos = config.getAppList();
        if(infos.length == 0) {
            AppInfo emptyInfo = new AppInfo("没有应用呢", null,
                    new Image(String.valueOf(App.class.getResource("image/fileNotFound.jpg"))),
                    null, "试着添加一点应用吧");
            appList.getChildren().add(new Module(emptyInfo, config));
        } else for(AppInfo info : infos) appList.getChildren().add(new Module(info, config));
    }

    private void initBackground() {
        double zoom = config.getZoom();
        double fitWidth = 700 * 0.28 * zoom;
        double fitHeight = 911 * 0.28 * zoom;

        Rectangle clip = new Rectangle(fitWidth, fitHeight);
        GaussianBlur blur = new GaussianBlur();

        blur.setRadius(15);
        clip.setArcHeight(20);
        clip.setArcWidth(20);

        pane.setStyle("-fx-padding: " + 10 * zoom + "px");
        appList.setSpacing(10 * zoom);
        setting.setStyle("-fx-font-size: " + 24 * zoom + "px;");

        background.setImage(new Image(String.valueOf(App.class.getResource("image/background.png"))));
        background.setFitHeight(fitHeight);
        background.setFitWidth(fitWidth);
        background.setClip(clip);
        background.setEffect(blur);
        // background.setVisible(false); /* debug */
    }
}