package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserManagementController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
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

        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getRole());
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
}
