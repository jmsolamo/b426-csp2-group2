package com.joysistvi.prestigecliapp.model;

public class MembershipCard {

    private int id;
    private int userId;
    private String cardCode;
    private double walletBalance;
    private String status;

    public MembershipCard() {
    }

    public MembershipCard(int id, int userId,
                          String cardCode,
                          double walletBalance,
                          String status) {
        this.id = id;
        this.userId = userId;
        this.cardCode = cardCode;
        this.walletBalance = walletBalance;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}