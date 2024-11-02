package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class UserManagementController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Administrator", "Doctor", "Patient");
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        // Check if the role is selected
        if (role == null) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please select a role.");
            return;
        }

        User user = new User(username, password, role);
        UserDAO userDAO = new UserDAO();

        if (userDAO.registerUser(user)) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Could not register user.");
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = UserDAO.authenticateUser(username, password);

        if (user != null) {
            loadUserInterface(user);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadUserInterface(User user) {
        FXMLLoader loader;
        Parent root;

        // Load different views based on user role
        switch (user.getRole()) {
            case "Administrator":
                loader = new FXMLLoader(getClass().getResource("/com/example/health_system/admin_view.fxml"));
                break;
            case "Doctor":
                loader = new FXMLLoader(getClass().getResource("/com/example/health_system/doctor_view.fxml"));
                break;
            case "Patient":
                loader = new FXMLLoader(getClass().getResource("/com/example/health_system/patient_view.fxml"));
                break;
            default:
                throw new IllegalArgumentException("Unexpected role: " + user.getRole());
        }

        try {
            root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow(); // Get the current stage from the login button
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load the user interface.");
        }
    }
}
