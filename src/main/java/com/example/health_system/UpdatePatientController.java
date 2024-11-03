package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdatePatientController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField diseaseField;

    private Patient patient; // The patient object to be updated

    public void setPatient(Patient patient) {
        this.patient = patient;
        usernameField.setText(patient.getUsername()); // Set username (not editable)
        diseaseField.setText(patient.getDisease()); // Populate disease field
    }

    @FXML
    private void handleUpdate() {
        String updatedDisease = diseaseField.getText();

        if (updatedDisease.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Disease field cannot be empty.");
            return;
        }

        // Logic to update the patient in the database
        var userDAO = new UserDAO();
        patient.setDisease(updatedDisease); // Update the disease in the object

        if (userDAO.updatePatient(patient)) {
            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Patient information updated successfully.");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Could not update patient information.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
