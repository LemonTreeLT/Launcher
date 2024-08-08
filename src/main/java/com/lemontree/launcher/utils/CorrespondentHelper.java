package com.lemontree.launcher.utils;

import com.lemontree.launcher.App;
import com.lemontree.launcher.stages.HomeStage;
import javafx.stage.Stage;

import java.net.URL;

public class CorrespondentHelper {
    public static Stage getApp() {
        return HomeStage.stagePub;
    }

    public static URL getResource(String path) {
        return App.class.getResource(path);
    }

}
