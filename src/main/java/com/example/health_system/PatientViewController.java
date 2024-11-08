package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientViewController {

    @FXML
    private VBox patientContainer; // The main container for patient functionalities
    @FXML
    private Button viewAppointmentsButton; // Button to view appointments
    @FXML
    private Button updateInfoButton; // Button to update personal information
    @FXML
    private Button logoutButton; // Button to log out

    @FXML
    public void initialize() {
        // Initialize any data or setup if needed
    }

    @FXML
    private void handleViewAppointments() {
        // Logic to view appointments
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Appointments");
        alert.setContentText("Functionality to view appointments will be implemented.");
        alert.showAndWait();
    }

    @FXML
    private void handleUpdateInfo() {
        // Logic to update personal information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Personal Information");
        alert.setContentText("Functionality to update personal information will be implemented.");
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        // Logic for logging out
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setContentText("You have been logged out successfully.");
        alert.showAndWait();

        // Load the main layout
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/health_system/main_layout.fxml"));
            Parent root = loader.load();

            // Get the current stage
            var stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root)); // Set the new scene
            stage.show(); // Show the updated scene
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Logout Failed", "Could not load the main layout.");
        }
    }

    @FXML
    private void handleRegisterNewPatient() {
        try {
            // Load the new patient registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/health_system/NewPatientRegistration.fxml"));
            Parent root = loader.load();

            // Set up a new stage for the form
            Stage registrationStage = new Stage();
            registrationStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
            registrationStage.setTitle("New Patient Registration");
            registrationStage.setScene(new Scene(root));
            registrationStage.showAndWait(); // Wait for the form to be closed before returning

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load the registration form.");
        }
    }

    @FXML
    private void handleViewPatients() {
        try {
            var loader = new FXMLLoader(getClass().getResource("/com/example/health_system/ViewPatients.fxml"));
            Parent root = loader.load();

            // Set up a new stage for viewing patients
            Stage patientsStage = new Stage();
            patientsStage.setTitle("Patients List");
            patientsStage.setScene(new Scene(root));
            patientsStage.show(); // Show the stage

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load the patients view.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
