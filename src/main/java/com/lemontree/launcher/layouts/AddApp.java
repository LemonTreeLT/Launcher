package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;
import com.lemontree.launcher.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddApp extends ImageView {
    public void init() {
        this.setImage(ImageUtils.White(new Image(String.valueOf(CorrespondentHelper.getResource("icon/add.png")))));

        double fitSize = 1948 * Config.getConfig().getZoom() * 0.01;

        this.setFitHeight(fitSize);
        this.setFitWidth(fitSize);


    }
}
