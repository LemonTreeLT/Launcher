package com.lemontree.launcher.layouts;

import com.lemontree.launcher.stages.SettingStage;
import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Setting extends Label {
    public static SettingStage settingStage;

    private void onMouseClicked(MouseEvent event) {
        switchVisible();
    }

    private void switchVisible() {
        if(settingStage.isShowing()) {
            settingStage.hide();
            CorrespondentHelper.getApp().requestFocus();
        } else settingStage.show();
    }

    private void layout(double zoom) {
        this.setStyle("-fx-font-size: " + 24 * zoom + "px;");
    }

    public Setting() {
        try {
            System.out.println("Setting buttom inited");
            settingStage = new SettingStage();
            this.setOnMouseClicked(this::onMouseClicked);
            Config.getConfig().addOnZoomChangedListener(this::layout);

            layout(Config.getConfig().getZoom());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
