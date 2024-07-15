module com.lemontree.launcher {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.lemontree.launcher to javafx.fxml;
    exports com.lemontree.launcher;
}