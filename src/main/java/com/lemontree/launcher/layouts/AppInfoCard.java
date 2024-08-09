package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.AppInfo;
import com.lemontree.launcher.utils.Config;
import com.lemontree.launcher.utils.CorrespondentHelper;
import com.lemontree.launcher.utils.LLogger;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AppInfoCard extends VBox {
    /* 组件对象 */
    private final ImageView appIcon = new ImageView();
    private final Label appName = new Label("Micros");
    private final Label appDescription = new Label("选择一个app以查看描述");

    private final Rectangle clip = new Rectangle();

    private final LLogger logger = new LLogger(this.getClass());

    /* 描述框对象 */
    private final Tooltip descriptionTooltip = new Tooltip();
    private final Tooltip nameTooltip = new Tooltip();

    public AppInfoCard() {
        // 设置初始化图标
        appIcon.setImage(new Image(String.valueOf(CorrespondentHelper.getResource("image/fileNotFound.jpg"))));

        // 设置自动换行和省略号
        appDescription.setStyle("-fx-text-overrun: ellipsis-word;");
        appDescription.setWrapText(true);
        appDescription.setAlignment(Pos.CENTER);
        appDescription.setTextFill(Color.WHITE);

        // 设置appName样式
        appName.setTextFill(Color.WHITE);

        // 将 appDescription 添加到 appDescriptionBox 中
        HBox appDescriptionBox = new HBox();
        appDescriptionBox.getChildren().add(appDescription);
        appDescriptionBox.setStyle("-fx-padding: 10 10 10 10");

        // 将appName添加到appNameBox中
        HBox appNameBox = new HBox();
        appNameBox.getChildren().add(appName);
        appNameBox.setStyle("-fx-padding: 0 10 0 10");

        getChildren().addAll(appIcon, appNameBox, appDescriptionBox);
        reLayout(Config.getConfig().getZoom());
        Config.getConfig().addOnZoomChangedListener(this::reLayout);

        // 设置鼠标悬停处理逻辑
        updateTooltip();
        appNameBox.setOnMouseEntered(this::nameOnMouseEntered);
        appNameBox.setOnMouseExited(this::nameOnMouseExited);
        appNameBox.setOnMouseMoved(this::nameOnMouseMoved);
        appDescriptionBox.setOnMouseEntered(this::descriptionOnMouseEntered);
        appDescriptionBox.setOnMouseExited(this::descriptionOnMouseExited);
        appDescriptionBox.setOnMouseMoved(this::descriptionOnMouseMoved);

        logger.info("AppInfoCard initialized");
    }

    private void descriptionOnMouseEntered(MouseEvent event) {
        descriptionTooltip.show(this,
                event.getScreenX() + 3,
                event.getScreenY() + 3);
    }

    private void nameOnMouseEntered(MouseEvent event) {
        nameTooltip.show(this,
                event.getScreenX() + 3,
                event.getScreenY() + 3);
    }

    private void descriptionOnMouseExited(MouseEvent event) {
        descriptionTooltip.hide();
    }

    private void nameOnMouseExited(MouseEvent event) {
        nameTooltip.hide();
    }

    private void descriptionOnMouseMoved(MouseEvent event) {
        descriptionTooltip.setX(event.getScreenX() + 3);
        descriptionTooltip.setY(event.getScreenY() + 3);
    }

    private void nameOnMouseMoved(MouseEvent event) {
        nameTooltip.setX(event.getScreenX() + 3);
        nameTooltip.setY(event.getScreenY() + 3);
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appIcon.setImage(appInfo.icon());
        this.appName.setText(appInfo.appName());
        this.appDescription.setText(appInfo.description());
        reLayout(Config.getConfig().getZoom());
        updateTooltip();
    }

    private void updateTooltip() {
        logger.info("description: " + descriptionTooltip.getText());
        logger.info("appName: " + appName.getText());
        this.descriptionTooltip.setText(this.appDescription.getText());
        this.nameTooltip.setText(this.appName.getText());
    }

    private void reLayout(double zoom) {
        double fitSize = 64 * 0.9 * zoom;

        clip.setWidth(fitSize);
        clip.setHeight(fitSize);
        clip.setArcHeight(20);
        clip.setArcWidth(20);

        appIcon.setClip(clip);
        appIcon.setFitHeight(fitSize);
        appIcon.setFitWidth(fitSize);

        appName.setStyle("-fx-font-size: " + 17 * zoom + "px;");

        appDescription.setStyle("-fx-font-size: " + 12 * zoom + "px;" + "-fx-text-overrun: ellipsis-word;");
    }
}
