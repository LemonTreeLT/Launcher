module com.lemontree.launcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.github.kwhat.jnativehook;
    requires com.alibaba.fastjson2;
    requires jdk.jsobject;
    requires javafx.swing;

    exports com.lemontree.launcher;
    exports com.lemontree.launcher.controllers;
    exports com.lemontree.launcher.utils;
    exports com.lemontree.launcher.layouts;
    opens com.lemontree.launcher.controllers to javafx.fxml;
    opens com.lemontree.launcher to javafx.fxml;
}