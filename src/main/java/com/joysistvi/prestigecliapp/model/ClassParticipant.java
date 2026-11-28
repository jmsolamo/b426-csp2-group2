package com.joysistvi.prestigecliapp.model;

import java.sql.Timestamp;

public class ClassParticipant{

    private int id;
    private int userId;
    private int fitnessClassId;
    private Timestamp enrollmentDate;
    private String status;
    private boolean archived;
    private Timestamp createdAt;


    public ClassParticipant(int userId, int fitnessClassId, String status) {
        this.userId = userId;
        this.fitnessClassId = fitnessClassId;
        this.status = status;
    }


    public ClassParticipant(int id, int userId, int fitnessClassId, Timestamp enrollmentDate,
                             String status, boolean archived, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.fitnessClassId = fitnessClassId;
        this.enrollmentDate = enrollmentDate;
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

    public int getFitnessClassId() {
        return fitnessClassId;
    }

    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
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

    public void setFitnessClassId(int fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
    }

    public void setEnrollmentDate(Timestamp enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
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