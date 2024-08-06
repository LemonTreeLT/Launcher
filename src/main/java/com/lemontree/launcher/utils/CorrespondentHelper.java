package com.lemontree.launcher.utils;

import com.lemontree.launcher.App;
import javafx.stage.Stage;

import java.net.URL;

public class CorrespondentHelper {
    public static Stage getApp() {
        return App.stagePub;
    }

    public static URL getResource(String path) {
        return App.class.getResource(path);
    }

}
