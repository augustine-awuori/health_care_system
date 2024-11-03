package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentsController {

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> patientColumn;
    @FXML
    private TableColumn<Appointment, String> doctorColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> dateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> timeColumn;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        // Set up the table columns to match the Appointment properties
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientUsername"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName")); // Corrected field name
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));

        loadAppointments();
    }

    private void loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("SELECT * FROM appointments")) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String patientUsername = resultSet.getString("patient_username");
                String doctorUsername = resultSet.getString("doctor_username");

                // Extract the timestamp and convert to LocalDate and LocalTime
                LocalDateTime dateTime = resultSet.getTimestamp("appointment_time").toLocalDateTime();
                LocalDate appointmentDate = dateTime.toLocalDate();
                LocalTime appointmentTime = dateTime.toLocalTime();

                // Create the Appointment object with only usernames for patient and doctor
                appointments.add(
                    new Appointment(patientUsername, doctorUsername, appointmentDate, appointmentTime.toString(), false)
                );
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load appointments.");
            e.printStackTrace();
        }

        appointmentsTable.getItems().setAll(appointments);
    }

    @FXML
    private void handleCancelAppointment() {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            // Logic to cancel the selected appointment
            showAlert(Alert.AlertType.INFORMATION, "Cancel Appointment", "Appointment cancelled successfully.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an appointment to cancel.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/health_system/main_layout.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
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
