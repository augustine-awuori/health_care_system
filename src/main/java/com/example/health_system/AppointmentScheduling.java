package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentScheduling {
    @FXML
    private TextField patientUsernameField; // Text field for patient username
    @FXML
    private ComboBox<String> doctorComboBox; // ComboBox for selecting the doctor
    @FXML
    private DatePicker appointmentDatePicker; // DatePicker for selecting the appointment date
    @FXML
    private TextField appointmentTimeField; // Text field for entering the appointment time
    @FXML
    private Button bookAppointmentButton;

    @FXML
    public void handleCancel() {}

    @FXML
    private void handleBookAppointment() {
        try {
            var loader = new FXMLLoader(getClass().getResource("appointment_booking.fxml"));
            Parent root = loader.load();

            var stage = (Stage) bookAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Appointment Booking");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Could not load the appointment booking scene.");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        patientUsernameField.clear();
        doctorComboBox.setValue(null);
        appointmentDatePicker.setValue(null);
        appointmentTimeField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleViewAppointments() {
        // Logic to view appointments
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("View Appointments");
        alert.setContentText("Viewing appointments functionality will be implemented.");
        alert.showAndWait();
    }
}
