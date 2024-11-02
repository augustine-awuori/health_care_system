module com.example.health_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.health_system to javafx.fxml;
    exports com.example.health_system;
}