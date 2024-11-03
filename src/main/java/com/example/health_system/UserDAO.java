package com.example.health_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String URL = "jdbc:mariadb://localhost:3306/health_system";
    private static final String USER = "root";
    private static final String PASSWORD = "rootpassword";

    public static User authenticateUser(String username, String password) {
        // Implement the logic to connect to the database and verify user credentials
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password); // Remember to hash passwords in a real application
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return new User(username, password, role);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return null; // Return null if user not found
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try (Connection connection = DatabaseConnector.getConnection();
            var statement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User validateUser(String username, String password) {
        String sql = "SELECT username, role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                return new User(username, password, role);
            } else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean saveNewPatient(Patient patient) {
        var sql = "INSERT INTO patients (username, disease, role) VALUES (?, ?, ?)";

        try (var conn = DatabaseConnector.getConnection();
             var statement = conn.prepareStatement(sql)) {

            statement.setString(1, patient.getUsername());
            statement.setString(2, patient.getDisease());
            statement.setString(3, "Patient");

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT id, username, disease, role FROM patients";

        try (var connection = DatabaseConnector.getConnection();
             var statement = connection.prepareStatement(query);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                var username = resultSet.getString("username");
                var disease = resultSet.getString("disease");
                patients.add(new Patient(username, disease));
            }
        }

        return patients;
    }

    public List<Patient> getPatientsByUsername(String username) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients WHERE username = ?";

        try (var conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Assuming you have appropriate constructors in Patient class
                var patient = new Patient(rs.getString("username"), rs.getString("disease"));
                patients.add(patient);
            }
        }

        return patients;
    }

    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET disease = ? WHERE username = ?";
        try (Connection connection = DatabaseConnector.getConnection(); // Assuming you have a method to get the connection
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getDisease()); // Set the disease field
            statement.setString(2, patient.getUsername()); // Set the username to find the patient

            int rowsUpdated = statement.executeUpdate(); // Execute the update
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of error
        }
    }

    public List<User> getAllDoctors() throws SQLException {
        List<User> doctors = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE role = 'Doctor'";

        try (var conn = DatabaseConnector.getConnection();
             var statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                doctors.add(new User(username, "Doctor"));
            }
        }

        return doctors;
    }

    public boolean patientExists(String username) throws SQLException {
        // Logic to check if the patient exists in the database
        return false;
    }

    public void bookAppointment(Appointment appointment) throws SQLException {
        // Logic to insert a new appointment record into the database
    }
}
