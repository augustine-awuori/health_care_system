package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminViewController {

    @FXML
    private VBox adminContainer; // The main container for admin functionalities
    @FXML
    private Button manageUsersButton; // Button to manage users
    @FXML
    private Button viewReportsButton; // Button to view reports
    @FXML
    private Button logoutButton;

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

        // Navigate back to the main layout
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/health_system/main_layout.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // Use logoutButton to get the window
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
