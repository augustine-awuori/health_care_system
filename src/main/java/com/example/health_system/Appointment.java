package com.example.health_system;

import java.time.LocalDate;

public class Appointment {
    private final String patientUsername;
    private final String doctorName;
    private final LocalDate appointmentDate;
    private final String appointmentTime;
    private boolean canceled;

    public Appointment(String patientUsername, String doctorName, LocalDate appointmentDate, String appointmentTime, boolean canceled) {
        this.patientUsername = patientUsername;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.canceled = canceled;
    }

    // Getters and Setters
    public String getPatientUsername() {
        return patientUsername;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
