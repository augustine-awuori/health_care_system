package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientLoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = UserDAO.authenticateUser(username, password);

        if (user != null) {
            if ("Patient".equals(user.getRole()))
                showAlert(Alert.AlertType.ERROR, "Login Failed", "You are not authorized to access this panel.");
             else loadPatientDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
        }
    }

    private void loadPatientDashboard() {
        // Load the patient dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/health_system/patient_view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load the patient dashboard.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
