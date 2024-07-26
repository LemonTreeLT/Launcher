package com.lemontree.launcher.layouts;

import com.lemontree.launcher.App;
import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;

public class Module extends HBox {
    private static final double TARGET_SIZE = 64.0;
    private static final double INITIAL_SIZE = 72.0;

    private final ImageView icon;
    private final Tooltip tooltip;

    private AppInfo info;
    private double zoom;

    public Module(AppInfo info, Config config) {
        this.zoom = config.getZoom();
        this.info = info;

        double fitSize = TARGET_SIZE * zoom * 0.504;

        ImageView icon = new ImageView(info.icon());
        Rectangle clip = new Rectangle(fitSize, fitSize);
        Label name = new Label(info.appName());
        VBox centerBox = new VBox();

        clip.setArcHeight(20);
        clip.setArcWidth(20);

        name.setFont(new Font("黑体", 17 * zoom));
        name.setTextFill(Color.WHITE);

        icon.setFitHeight(fitSize);
        icon.setFitWidth(fitSize);
        icon.setClip(clip);

        centerBox.getChildren().add(name);
        centerBox.setAlignment(Pos.CENTER);

        this.icon = icon;
        this.setSpacing(5.6 * zoom);
        this.getChildren().addAll(icon, centerBox);

        this.setOnMouseEntered(this::onMouseEntered);
        this.setOnMouseExited(this::onMouseExited);
        this.setOnMouseClicked(this::launch);

        if(info.description() != null) {
            this.tooltip = new Tooltip(info.description());
            this.setOnMouseMoved(this::onMouseMoved);
        } else this.tooltip = null;
    }

    public void requestLayout() {

    }

    public void requestLayout(double zoom, AppInfo info) {

    }


    private void onMouseMoved(MouseEvent event) {
        tooltip.setX(event.getScreenX() - icon.getBoundsInLocal().getWidth() / 2 + 15);
        tooltip.setY(event.getScreenY() - icon.getBoundsInLocal().getHeight() / 2 + 15);
    }

    private void onMouseEntered(MouseEvent event) {
        zoomImageView(icon, INITIAL_SIZE);
        if(tooltip != null) tooltip.show(this,
                event.getScreenX() + 3,
                event.getScreenY() + 3);
    }

    private void onMouseExited(MouseEvent event) {
        zoomImageView(icon, TARGET_SIZE);
        if(tooltip != null) tooltip.hide();
    }

    private void launch(MouseEvent event) {
        launchAnimation();

        ProcessBuilder pb = new ProcessBuilder(info.launchPath());
        try {
            pb.start();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void launchAnimation() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), this);
        TranslateTransition slideBack = new TranslateTransition(Duration.millis(150), this);

        slideOut.setToX(200 * zoom);
        slideBack.setToX(0 * zoom);

        slideOut.setOnFinished(event -> {
            App.stage.hide();
            slideBack.play();
        });
        slideOut.play();
    }

    private void zoomImageView(ImageView imageView, double initialSize) {
        double scale = (initialSize * zoom * 0.504) / (TARGET_SIZE * zoom * 0.504);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), imageView);
        scaleTransition.setToX(scale);
        scaleTransition.setToY(scale);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
    }
}
