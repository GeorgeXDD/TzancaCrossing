module com.example.tzancashootingv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.java;
    requires jdk.internal.le;

    opens com.example.tzancashootingv1 to javafx.fxml;
    exports com.example.tzancashootingv1;
}