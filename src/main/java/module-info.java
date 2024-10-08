module com.lemontree.launcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.github.kwhat.jnativehook;
    requires com.alibaba.fastjson2;
    requires javafx.swing;
    requires org.jetbrains.annotations;

    exports com.lemontree.launcher;
    exports com.lemontree.launcher.controllers;
    exports com.lemontree.launcher.utils;
    exports com.lemontree.launcher.layouts;
    exports com.lemontree.launcher.stages;
    opens com.lemontree.launcher.controllers to javafx.fxml;
    opens com.lemontree.launcher to javafx.fxml;
    exports com.lemontree.launcher.events;
}