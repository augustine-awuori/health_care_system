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
    private TextField patientUsernameField;
    @FXML
    private ComboBox<String> doctorComboBox;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private TextField appointmentTimeField;
    @FXML
    private Button bookAppointmentButton;
    @FXML
    private Button viewAppointmentsButton;

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
        try {
            var loader = new FXMLLoader(getClass().getResource("view_appointments.fxml"));
            Parent root = loader.load();

            var stage = (Stage) viewAppointmentsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Appointments");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Error", "Could not load the appointment booking scene.");
            e.printStackTrace();
        }
    }
}
