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
import java.sql.SQLException;
import java.time.LocalDate;

public class ViewInvoicesController {

    @FXML
    private TableView<Invoice> invoicesTable;
    @FXML
    private TableColumn<Invoice, String> usernameColumn;
    @FXML
    private TableColumn<Invoice, Integer> amountColumn;
    @FXML
    private TableColumn<Invoice, String> paymentModeColumn;
    @FXML
    private TableColumn<Invoice, LocalDate> dateColumn;
    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentModeColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMode"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadInvoices();
    }

    private void loadInvoices() {
        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement("SELECT * FROM invoices")) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var username = resultSet.getString("patient_username");
                int amount = resultSet.getInt("amount");
                var paymentMode = resultSet.getString("payment_mode");
                java.sql.Timestamp sqlTimestamp = resultSet.getTimestamp("payment_date"); // Changed to payment_date

                // Convert to LocalDate if sqlTimestamp is not null
                LocalDate paymentDate = (sqlTimestamp != null) ? sqlTimestamp.toLocalDateTime().toLocalDate() : null;

                // Add the Invoice to the table if paymentDate is not null
                if (username != null && paymentMode != null && paymentDate != null) {
                    invoicesTable.getItems().add(new Invoice(username, amount, paymentMode, paymentDate));
                } else {
                    System.out.println("Null value found in result set, skipping this record.");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load invoices: " + e.getMessage());
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
