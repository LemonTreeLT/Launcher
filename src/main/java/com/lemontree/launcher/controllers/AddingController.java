package com.lemontree.launcher.controllers;

import com.lemontree.launcher.layouts.Background;
import com.lemontree.launcher.layouts.TitleBar;
import com.lemontree.launcher.utils.CorrespondentHelper;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingController implements Initializable {
    public Background background;
    public TitleBar titleBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setImagePath(CorrespondentHelper.getResource("image/backgroundBig.jpg"));
    }
}
