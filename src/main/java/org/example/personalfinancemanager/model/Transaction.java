package org.example.personalfinancemanager.model;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private String type;
    private String bankName;
    private double amount;
    private String category;
    private String note;
    private LocalDate date;

    public Transaction(int id, String type, String bankName, double amount, String category, String note, LocalDate date) {
        this.id = id;
        this.type = type;
        this.bankName = bankName;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
