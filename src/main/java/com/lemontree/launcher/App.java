package com.lemontree.launcher;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import com.lemontree.launcher.utils.Config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App extends Application {
    public static Stage stage;
    public static Config config = new Config();

    @Override
    public void start(Stage stage) throws IOException {
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("root.fxml"));
        StackPane root = fxmlLoader.load();

        Scene scene = new Scene(root, 320, 240);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("css/style.css")));

        KeyCombination keyCombination = new KeyCodeCombination(
                KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN
        );

        scene.getAccelerators().put(keyCombination, this::switchVisible);

        double zoom = config.getZoom();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Launcher");
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.setWidth(700 * 0.28 * zoom);
        stage.setHeight(911 * 0.28 * zoom);
        stage.show();
        App.stage = stage;
    }

    public void switchVisible() {
        if(App.stage.isShowing()) App.stage.hide();
        else {
            int offset = config.getMouseOffset();
            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            App.stage.setX(mouseLocation.getX() + offset);
            App.stage.setY(mouseLocation.getY() + offset);
            App.stage.show();
        }
    }

    @Override
    public void stop() {
        System.out.println("Appliction is stopping");
        try {
            GlobalScreen.unregisterNativeHook();
        } catch(NativeHookException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}