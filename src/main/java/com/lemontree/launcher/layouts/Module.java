package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;

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
import java.nio.file.Files;
import java.nio.file.Path;

public class Module extends HBox {
    protected static final double TARGET_SIZE = 64.0;
    protected static final double INITIAL_SIZE = 72.0;

    protected final ImageView icon;
    protected final Tooltip tooltip;
    protected final AppInfo info;

    protected double zoom;

    public Module(AppInfo info) {
        this.zoom = Config.getConfig().getZoom();
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

        tooltip = new Tooltip(getDescription());
        if(info.description() != null) this.setOnMouseMoved(this::onMouseMoved);

        Config.getConfig().addOnZoomChangedListener(this::reLayout);
    }

    public void reLayout(double zoom) {
        double fitSize = TARGET_SIZE * zoom * 0.504;

        Rectangle clip = (Rectangle) icon.getClip();
        clip.setWidth(fitSize);
        clip.setHeight(fitSize);

        icon.setClip(clip);
        icon.setFitHeight(fitSize);
        icon.setFitWidth(fitSize);

        this.zoom = Config.getConfig().getZoom();
        this.setSpacing(5.6 * zoom);
    }

    private void onMouseMoved(MouseEvent event) {
        tooltip.setX(event.getScreenX() - icon.getBoundsInLocal().getWidth() / 2 + 15);
        tooltip.setY(event.getScreenY() - icon.getBoundsInLocal().getHeight() / 2 + 15);
    }

    private void onMouseEntered(MouseEvent event) {
        zoomImageView(icon, INITIAL_SIZE);
        if(tooltip != null) tooltip.show(
                this,
                event.getScreenX() + 3,
                event.getScreenY() + 3);
    }

    private void onMouseExited(MouseEvent event) {
        zoomImageView(icon, TARGET_SIZE);
        if(tooltip != null) tooltip.hide();
    }

    protected void launch(MouseEvent event) {
        if(info.launchPath() != null && Files.exists(Path.of(info.launchPath()))) {
            launchAnimation();

            ProcessBuilder pb = new ProcessBuilder(info.launchPath());
            try {
                pb.start();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        } else errorAnimation();
    }

    private void errorAnimation() {
        double durationMillis = 100;
        double offset = 50 * zoom;

        TranslateTransition r1 = createTransition(durationMillis, offset);
        TranslateTransition r2 = createTransition(durationMillis, offset);
        TranslateTransition r3 = createTransition(durationMillis, offset);
        TranslateTransition l1 = createTransition(durationMillis, -offset);
        TranslateTransition l2 = createTransition(durationMillis, -offset);
        TranslateTransition l3 = createTransition(durationMillis, 0);

        r1.setOnFinished(event -> {
            l1.play();
            tooltip.setText("这个应用的路径好像出错了,重新添加吧");
        });
        l1.setOnFinished(event -> r2.play());
        r2.setOnFinished(event -> l2.play());
        l2.setOnFinished(event -> r3.play());
        r3.setOnFinished(event -> l3.play());
        l3.setOnFinished(event -> tooltip.setText(getDescription()));

        r1.play();
    }

    private String getDescription() {
        if(info.description() != null) return info.description();
        else return "没有描述呢";
    }

    private TranslateTransition createTransition(double durationMillis, double toX) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(durationMillis), this);
        transition.setToX(toX);
        return transition;
    }

    private void launchAnimation() {
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), this);
        TranslateTransition slideBack = new TranslateTransition(Duration.millis(150), this);

        slideOut.setToX(200 * zoom);
        slideBack.setToX(0 * zoom);

        slideOut.setOnFinished(event -> {
            CorrespondentHelper.getApp().hide();
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
