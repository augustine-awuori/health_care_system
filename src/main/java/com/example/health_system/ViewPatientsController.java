package com.example.health_system;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ViewPatientsController {

    @FXML
    private TableView<Patient> patientsTable; // TableView to display patients
    @FXML
    private TableColumn<Patient, Integer> idColumn; // Column for patient ID
    @FXML
    private TableColumn<Patient, String> usernameColumn; // Column for username
    @FXML
    private TableColumn<Patient, String> diseaseColumn; // Column for disease
    @FXML
    private TableColumn<Patient, String> roleColumn; // Column for role

    @FXML
    public void initialize() {
        // Initialize columns
//        idColumn.setCellValueFactory(cellData -> cellData.getValue().getPatientId());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getPatientUsername());
        diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().getDiseaseProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());

        loadPatientData();
    }

    private void loadPatientData() {
        try {
            UserDAO userDAO = new UserDAO();
            ObservableList<Patient> patients = FXCollections.observableArrayList(userDAO.getAllPatients());
            patientsTable.setItems(patients);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load patient data.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        // Logic to close the window
        var stage = (Stage) patientsTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
