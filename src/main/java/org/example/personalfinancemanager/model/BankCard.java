package org.example.personalfinancemanager.model;


public class BankCard {

    private int id;
    private String bankName;
    private String imagePath;


    public BankCard(int id, String bankName, String imagePath) {
        this.id = id;
        this.bankName = bankName;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
