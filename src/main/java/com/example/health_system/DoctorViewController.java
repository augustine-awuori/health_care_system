package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DoctorViewController {

    @FXML
    private VBox doctorContainer; // The main container for doctor functionalities
    @FXML
    private Button viewPatientsButton; // Button to view patients
    @FXML
    private Button updateRecordsButton; // Button to update records
    @FXML
    private Button logoutButton; // Button to log out

    @FXML
    public void initialize() {
        // Initialize any data or setup if needed
    }

    @FXML
    private void handleViewPatients() {
        // Logic to view all patients
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Patients");
        alert.setContentText("Functionality to view patients will be implemented.");
        alert.showAndWait();
    }

    @FXML
    private void handleUpdateRecords() {
        // Logic to update medical records
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Records");
        alert.setContentText("Functionality to update records will be implemented.");
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        // Logic for logging out
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setContentText("You have been logged out successfully.");
        alert.showAndWait();

        // Add logic here to navigate back to the login screen if needed
    }
}
