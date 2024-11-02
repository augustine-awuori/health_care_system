package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void showUserManagement() {
        loadModule("user_management.fxml");
    }

    @FXML
    private void showPatientManagement() {
        loadModule("patient_management.fxml");
    }

    @FXML
    private void showAppointmentScheduling() {
        loadModule("appointment_scheduling.fxml");
    }

    @FXML
    private void showBilling() {
        loadModule("billing.fxml");
    }

    private void loadModule(String fxmlFile) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlFile));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            showAlert("Error loading module", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
