package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AppointmentScheduling {

    @FXML
    private void handleBookAppointment() {
        // Logic to book an appointment
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Book Appointment");
        alert.setContentText("Booking appointment functionality will be implemented.");
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
