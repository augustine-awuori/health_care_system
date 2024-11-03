package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppointmentBookingController {

    @FXML
    private TextField patientUsernameField;
    @FXML
    private ComboBox<String> doctorComboBox;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private TextField appointmentTimeField;

    @FXML
    public void initialize() {
        loadDoctors();
    }

    private void loadDoctors() {
        try {
            var userDAO = new UserDAO();
            List<User> doctorList = userDAO.getAllDoctors();

            // Populate ComboBox with doctor usernames
            doctorList.forEach(doctor -> doctorComboBox.getItems().add(doctor.getUsername()));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load doctor data.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookAppointment() {
        String patientUsername = patientUsernameField.getText().trim(); // Using the correct field
        String selectedDoctor = doctorComboBox.getValue();
        String appointmentDate = appointmentDatePicker.getValue() != null ? appointmentDatePicker.getValue().toString() : null;
        String appointmentTime = appointmentTimeField.getText().trim(); // Using the correct time field

        // Validate input
        if (patientUsername.isEmpty() || selectedDoctor == null || appointmentDate == null || appointmentTime.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        // Check if patient exists
        if (!isPatientExists(patientUsername)) {
            showAlert(Alert.AlertType.ERROR, "Booking Failed", "Patient not found or doesn't exist.");
            return;
        }

        // Create the appointment table if it doesn't exist
        createAppointmentTableIfNotExists();

        // Book the appointment
        if (bookAppointment(patientUsername, selectedDoctor, appointmentDate + " " + appointmentTime)) { // Combine date and time
            showAlert(Alert.AlertType.INFORMATION, "Booking Successful", "Appointment booked successfully.");
            clearFields(); // Optionally clear fields after booking
        } else {
            showAlert(Alert.AlertType.ERROR, "Booking Failed", "Could not book the appointment.");
        }
    }

    private boolean isPatientExists(String username) {
        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("SELECT COUNT(*) FROM patients WHERE username = ?")) {
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

    private void createAppointmentTableIfNotExists() {
        try (Connection connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS appointments (id INT AUTO_INCREMENT PRIMARY KEY, patient_username VARCHAR(255), doctor_username VARCHAR(255), appointment_time DATETIME, canceled BOOLEAN DEFAULT FALSE)")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean bookAppointment(String username, String doctor, String time) {
        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("INSERT INTO appointments (patient_username, doctor_username, appointment_time) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, doctor);
            statement.setString(3, time);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void clearFields() {
        patientUsernameField.clear();
        doctorComboBox.setValue(null);
        appointmentDatePicker.setValue(null);
        appointmentTimeField.clear();
    }

    @FXML
    private void handleCancel() {
        // Logic to close or reset the booking form
        clearFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
