package com.lemontree.launcher.layouts;

import com.lemontree.launcher.utils.CorrespondentHelper;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class AddApp extends Label {
    public AddApp() {
        this.setOnMouseEntered(this::enterAnimation);
        this.setOnMouseExited(this::exitAnimation);
        this.setOnMouseClicked(this::onMouseClicked);
    }

    public void onMouseClicked(MouseEvent e) {
        startAnimation();
        CorrespondentHelper.getSetting().switchSceneToAppAdding();

    }

    private void enterAnimation(MouseEvent event) {
        FadeTransition fade = new FadeTransition(Duration.millis(80), this);
        fade.setToValue(0.5);
        fade.play();
    }

    // 恢复到原始状态，带有动画效果
    private void exitAnimation(MouseEvent event) {
        FadeTransition fade = new FadeTransition(Duration.millis(80), this);
        fade.setToValue(1);
        fade.play();
    }

    private void startAnimation() {
        // 创建缩小动画
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(70), this);
        scaleDown.setToX(0.7);
        scaleDown.setToY(0.7);

        // 创建放大动画
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(70), this);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);

        scaleDown.setOnFinished(event -> scaleUp.play());

        scaleDown.play();
    }
}
