package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Setting extends Label {
    private void onMouseClicked(MouseEvent event) {
        switchVisible();
    }

    private void switchVisible() {
        Stage settingStage = CorrespondentHelper.getSetting();

        if(settingStage.isShowing()) {
            settingStage.hide();
            CorrespondentHelper.getApp().requestFocus();
        } else settingStage.show();
    }

    private void layout(double zoom) {
        this.setStyle("-fx-font-size: " + 24 * zoom + "px;");
    }

    public Setting() {
        this.setOnMouseClicked(this::onMouseClicked);
        Config.getConfig().addOnZoomChangedListener(this::layout);

        layout(Config.getConfig().getZoom());
    }
}
