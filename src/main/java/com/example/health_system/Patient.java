package com.example.health_system;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient extends User {
    private SimpleStringProperty disease;
    private final String patientId;

    public Patient(String username, String disease) {
        super(username, "", "Patient"); // Call the superclass constructor
        this.disease = new SimpleStringProperty(disease); // Initialize disease property
        this.patientId = generatePatientId(); // Generate patient ID
    }

    public String getDisease() {
        return disease.get();
    }

    public StringProperty getDiseaseProperty() {
        return disease;
    }

    public StringProperty getRoleProperty() {
        return new SimpleStringProperty(this.getRole());
    }

    public SimpleStringProperty getPatientId() {
        return new SimpleStringProperty(patientId);
    }

    public SimpleStringProperty getPatientUsername() {
       return new SimpleStringProperty(this.getUsername());
    }

    private String generatePatientId() {
        // Implement ID generation logic, for example, using UUID or a counter
        return "PAT-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }

    public void setDisease(String updatedDisease) {
        disease = new SimpleStringProperty(updatedDisease);
    }
}
