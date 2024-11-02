package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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

        // You might want to add logic here to navigate back to the login screen
    }
}
