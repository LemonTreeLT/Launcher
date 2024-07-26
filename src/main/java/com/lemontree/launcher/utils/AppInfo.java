package com.lemontree.launcher.utils;

import javafx.scene.image.Image;

public record AppInfo(String appName, String launchPath, Image icon, String id, String description) {
    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", launchPath='" + launchPath + '\'' +
                ", id=" + id +
                '}';
    }
}
