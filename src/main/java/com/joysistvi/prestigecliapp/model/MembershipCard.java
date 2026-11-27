package com.joysistvi.prestigecliapp.model;

public class MembershipCard {

    private int id;
    private int memberId;
    private String cardNumber;
    private String issueDate;
    private String expiryDate;

    public MembershipCard() {}

    public MembershipCard(int id, int memberId, String cardNumber,
                          String issueDate, String expiryDate) {
        this.id = id;
        this.memberId = memberId;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    // Generate getters and setters
}