package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingController {

    @FXML
    private TextField patientUsernameField;
    @FXML
    private ComboBox<String> paymentModeComboBox;
    @FXML
    private Label amountLabel;

    private static final int PAYMENT_AMOUNT = 2000;

    @FXML
    public void initialize() {
        paymentModeComboBox.getItems().addAll("Online", "Cash");
    }

    @FXML
    private void handleSearchPatient() {
        String username = patientUsernameField.getText();

        if (isPatient(username)) {
            if (hasActiveAppointment(username))
                showAlert(Alert.AlertType.INFORMATION, "Patient Found", "Patient has active appointments.");
            else
                showAlert(Alert.AlertType.ERROR, "No Appointments", "Patient has no active appointments.");
        } else
            showAlert(Alert.AlertType.ERROR, "Not a Patient", "The user does not exist or is not a patient.");
    }

    private String checkUserRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role"); // Return the role if the user exists
            } else {
                return null; // Return null if the user does not exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isPatient(String username) {
        String query = "SELECT 1 FROM patients WHERE username = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // True if user exists in the patient table
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean hasActiveAppointment(String username) {
        String query = "SELECT COUNT(*) FROM appointments WHERE patient_username = ? AND canceled = FALSE";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void handleProcessPayment() {
        String username = patientUsernameField.getText();
        String paymentMode = paymentModeComboBox.getValue();

        if (paymentMode == null) {
            showAlert(Alert.AlertType.WARNING, "Payment Mode Required", "Please select a payment mode.");
            return;
        }

        // Save the payment details as an invoice
        if (createInvoice(username, PAYMENT_AMOUNT, paymentMode)) {
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", "Payment has been processed successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Payment Failed", "An error occurred while processing the payment.");
        }
    }

    private boolean createInvoice(String username, int amount, String paymentMode) {
        createInvoicesTableIfNotExists();

        String query = "INSERT INTO invoices (patient_username, amount, payment_mode) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setInt(2, amount);
            statement.setString(3, paymentMode);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createInvoicesTableIfNotExists() {
        String createTableQuery = """
        CREATE TABLE IF NOT EXISTS invoices (
            id INT AUTO_INCREMENT PRIMARY KEY,
            patient_username VARCHAR(50) NOT NULL,
            amount INT NOT NULL,
            payment_mode VARCHAR(10) NOT NULL,
            payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    """;
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
