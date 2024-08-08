package com.lemontree.launcher;

import com.lemontree.launcher.stages.HomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    private HomeStage primaryStage;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = new HomeStage(primaryStage);
    }

    @Override
    public void stop(){
        primaryStage.stop();
    }
}