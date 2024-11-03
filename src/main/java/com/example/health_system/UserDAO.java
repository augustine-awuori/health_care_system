package com.example.health_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
