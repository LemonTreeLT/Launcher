package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.CorrespondentHelper;

import com.lemontree.launcher.utils.LLogger;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    private boolean isInited = false;
    private Stage settingStage;

    public TitleBar() {
        this.setOnMousePressed(event -> {
            init();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            settingStage.setX(event.getScreenX() - xOffset);
            settingStage.setY(event.getScreenY() - yOffset);
        });
    }

    private void init() {
        LLogger logger = new LLogger(this.getClass());

        if (isInited) return;
        if(CorrespondentHelper.getSetting() == null) {
            logger.warning("Setting stage might not be initialized");
            return;
        }
        settingStage = CorrespondentHelper.getSetting();
        logger.info("Inited TitleBar");
        isInited = true;
    }
}
