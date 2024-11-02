package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminViewController {

    @FXML
    private VBox adminContainer; // The main container for admin functionalities
    @FXML
    private Button manageUsersButton; // Button to manage users
    @FXML
    private Button viewReportsButton; // Button to view reports
    @FXML
    private Button logoutButton; // Button to log out

    @FXML
    public void initialize() {
        // Initialize any data or setup if needed
    }

    @FXML
    private void handleManageUsers() {
        // Logic to manage users
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Manage Users");
        alert.setContentText("Functionality to manage users will be implemented.");
        alert.showAndWait();
    }

    @FXML
    private void handleViewReports() {
        // Logic to view reports
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Reports");
        alert.setContentText("Functionality to view reports will be implemented.");
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
