package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewPatientRegistrationController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField diseaseField;

    @FXML
    private void handleSave() {
        String username = usernameField.getText();
        String disease = diseaseField.getText();

        if (username.isEmpty() || disease.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        var newPatient = new Patient(username, disease);

        var userDAO = new UserDAO();

        if (userDAO.saveNewPatient(newPatient)) {
            showAlert(Alert.AlertType.INFORMATION, "Save Successful", "Patient saved successfully.");

            // Clear fields after saving
            usernameField.clear();
            diseaseField.clear();
        } else
            showAlert(Alert.AlertType.ERROR, "Save Failed", "Could not save patient. Please try again.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
