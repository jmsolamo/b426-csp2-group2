package com.joysistvi.prestigecliapp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class SubscribedMember {

    private int id;
    private int userId;
    private int membershipPlanId;
    private Date startDate;
    private Date expirationDate;
    private String status;
    private boolean archived;
    private Timestamp createdAt;


    public SubscribedMember(int userId, int membershipPlanId, Date startDate,
                             Date expirationDate, String status) {
        this.userId = userId;
        this.membershipPlanId = membershipPlanId;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.status = status;
    }


    public SubscribedMember(int id, int userId, int membershipPlanId, Date startDate,
                             Date expirationDate, String status, boolean archived,
                             Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.membershipPlanId = membershipPlanId;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.archived = archived;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMembershipPlanId() {
        return membershipPlanId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean isArchived() {
        return archived;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMembershipPlanId(int membershipPlanId) {
        this.membershipPlanId = membershipPlanId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}