package com.example.health_system;

import java.time.LocalDate;

public class Invoice {
    private String username;
    private int amount;
    private String paymentMode;
    private LocalDate date;

    public Invoice(String username, int amount, String paymentMode, LocalDate date) {
        this.username = username;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.date = date;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
