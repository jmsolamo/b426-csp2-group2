package com.joysistvi.prestigecliapp.model;

import java.sql.Timestamp;

public class PurchaseHistory {
    private int id;
    private int membershipCardId;
    private String purchaseReference;
    private String purchaseType;
    private int itemId;
    private double amount;
    private String status;
    private Timestamp purchaseDate;


    public PurchaseHistory(int membershipCardId, String purchaseReference, String purchaseType, int itemId, double amount,
                           String status, Timestamp purchaseDate) {
        this.membershipCardId = membershipCardId;
        this.purchaseReference = purchaseReference;
        this.purchaseType = purchaseType;
        this.itemId = itemId;
        this.amount = amount;
        this.status = status;
        this.purchaseDate = purchaseDate;
    }


    public PurchaseHistory(int id, int membershipCardId, String purchaseReference, String purchaseType, int itemId, double amount,
                           String status, Timestamp purchaseDate) {
        this.id = id;
        this.membershipCardId = membershipCardId;
        this.purchaseReference = purchaseReference;
        this.purchaseType = purchaseType;
        this.itemId = itemId;
        this.amount = amount;
        this.status = status;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(int membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public String getPurchaseReference() {
        return purchaseReference;
    }

    public void setPurchaseReference(String purchaseReference) {
        this.purchaseReference = purchaseReference;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
