package com.joysistvi.prestigecliapp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TopUpHistory {
    private int id;
    private int membershipCardId;
    private String topupReference;
    private String paymentReference;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private Timestamp topupDate;

    // Joined display fields
    private int userId;
    private String memberName;
    private String cardNo;

    public TopUpHistory() {
    }

    public TopUpHistory(int membershipCardId, String topupReference, String paymentReference,
                        BigDecimal amount, String paymentMethod, String status) {
        this.membershipCardId = membershipCardId;
        this.topupReference = topupReference;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMembershipCardId() { return membershipCardId; }
    public void setMembershipCardId(int membershipCardId) { this.membershipCardId = membershipCardId; }

    public String getTopupReference() { return topupReference; }
    public void setTopupReference(String topupReference) { this.topupReference = topupReference; }

    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getTopupDate() { return topupDate; }
    public void setTopupDate(Timestamp topupDate) { this.topupDate = topupDate; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }
}