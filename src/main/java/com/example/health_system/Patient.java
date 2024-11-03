package com.example.health_system;

public class Patient extends User {
    private String disease;
    private final String patientId;

    public Patient(String username, String disease) {
        super(username, "", "Patient");
        this.disease = disease;
        this.patientId = generatePatientId();
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPatientId() {
        return patientId;
    }

    private String generatePatientId() {
        // Implement ID generation logic, for example, using UUID or a counter
        return "PAT-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
