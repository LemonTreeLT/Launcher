package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.Config;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class Background extends ImageView {
    public void init(Config config, @Nullable URL imagePath) {
        double zoom = config.getZoom();

        if (imagePath == null) throw new NullPointerException("imagePath is null");

        Image image = new Image(imagePath.toString());

        double fitWidth = image.getWidth() * 0.28 * zoom;
        double fitHeight = image.getHeight() * 0.28 * zoom;

        Rectangle clip = new Rectangle(fitWidth, fitHeight);
        GaussianBlur blur = new GaussianBlur();

        blur.setRadius(15);
        clip.setArcHeight(20);
        clip.setArcWidth(20);

        this.setImage(image);
        this.setFitHeight(fitHeight);
        this.setFitWidth(fitWidth);
        this.setClip(clip);
        this.setEffect(blur);
    }
}
