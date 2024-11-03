package com.example.health_system;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class ViewPatientsController {

    @FXML
    private TableView<Patient> patientsTable; // TableView to display patients
    @FXML
    private TableColumn<Patient, String> idColumn; // Column for ID
    @FXML
    private TableColumn<Patient, String> usernameColumn; // Column for username
    @FXML
    private TableColumn<Patient, String> diseaseColumn; // Column for disease
    @FXML
    private TableColumn<Patient, String> roleColumn; // Column for role
    @FXML
    private TextField searchField; // TextField for search input
    @FXML
    private TableColumn<Patient, Void> actionsColumn; // Column for action buttons

    @FXML
    public void initialize() {
        // Initialize columns
//        idColumn.setCellValueFactory(cellData -> cellData.getValue().getPatientId());
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getPatientUsername());
        diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().getDiseaseProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());

        loadPatientData();
        // Create the action column with update buttons
        createActionsColumn();

        // Load patient data into the table
        loadPatientData();
    }

    private void createActionsColumn() {
        actionsColumn.setCellFactory(new Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>>() {
            @Override
            public TableCell<Patient, Void> call(TableColumn<Patient, Void> param) {
                return new TableCell<Patient, Void>() {
                    private final Button updateButton = new Button("Update");

                    {
                        updateButton.setOnAction(event -> {
                            Patient selectedPatient = getTableView().getItems().get(getIndex());
                            handleUpdate(selectedPatient); // Call update handler with selected patient
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(updateButton);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleUpdate(Patient patient) {
        try {
            var loader = new FXMLLoader(getClass().getResource("/com/example/health_system/UpdatePatient.fxml"));
            Parent root = loader.load();

            UpdatePatientController controller = loader.getController();
            controller.setPatient(patient);

            var updateStage = new Stage();
            updateStage.setTitle("Update Patient - " + patient.getUsername());
            updateStage.setScene(new Scene(root));
            updateStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Loading Failed", "Could not load the update form.");
        }
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
    private void handleSearch() {
        String username = searchField.getText().trim();
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter a username to search.");
            return;
        }

        try {
            var userDAO = new UserDAO();
            ObservableList<Patient> patients = FXCollections.observableArrayList(userDAO.getPatientsByUsername(username));

            if (patients.isEmpty())
                showAlert(Alert.AlertType.INFORMATION, "No Results", "No patients found with that username.");
            else  patientsTable.setItems(patients);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Search Failed", "Could not perform search.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReset() {
        searchField.clear(); // Clear the search field
        loadPatientData(); // Reload all patient data
    }

    @FXML
    private void handleClose() {
        var stage = (Stage) patientsTable.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleTableClick(MouseEvent mouseEvent) {

    }
}
