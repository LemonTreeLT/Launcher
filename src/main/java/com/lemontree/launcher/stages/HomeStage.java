package com.lemontree.launcher.stages;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import com.lemontree.launcher.utils.Config;

import com.lemontree.launcher.utils.CorrespondentHelper;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HomeStage extends Stage {
    public static Stage stagePub;
    private final Stage stage;

    public HomeStage(Stage primaryStage) {
        this.stage = primaryStage;
        HomeStage.stagePub = primaryStage;

        Platform.setImplicitExit(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch(NativeHookException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("There was a problem registering the native hook.");
            throw new RuntimeException(e);
        }

        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0 &&
                        (e.getModifiers() & NativeKeyEvent.SHIFT_MASK) != 0 &&
                        e.getKeyCode() == NativeKeyEvent.VC_Q) Platform.runLater(() -> switchVisible());
            }
        });

        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        FXMLLoader fxmlLoader = new FXMLLoader(CorrespondentHelper.getResource("root.fxml"));
        StackPane root;

        try {
            root = fxmlLoader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(String.valueOf(CorrespondentHelper.getResource("css/style.css")));

        double zoom = Config.getConfig().getZoom();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Launcher");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.setWidth(700 * 0.28 * zoom);
        primaryStage.setHeight(911 * 0.28 * zoom);

        addFadeInAnimation(stage);
        addFadeOutAnimation(stage);
    }

    private void addFadeInAnimation(Stage stage) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(100));
        fadeIn.setNode(stage.getScene().getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        stage.setOnShowing(event -> fadeIn.play());
    }

    private void addFadeOutAnimation(Stage stage) {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(100));
        fadeIn.setNode(stage.getScene().getRoot());
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);

        stage.setOnHidden(event -> fadeIn.play());
    }

    public void switchVisible() {
        if(stage.isShowing()) stage.hide();
        else {
            int offset = Config.getConfig().getMouseOffset();
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            stage.requestFocus();
            stage.setX(mouseLocation.getX() + offset);
            stage.setY(mouseLocation.getY() + offset);
            stage.show();
        }
    }

    public void stop() {
        System.out.println("Appliction is stopping");
        try {
            GlobalScreen.unregisterNativeHook();
        } catch(NativeHookException e) {
            throw new RuntimeException(e);
        }
    }
}