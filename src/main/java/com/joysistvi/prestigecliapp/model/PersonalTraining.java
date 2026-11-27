package com.joysistvi.prestigecliapp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PersonalTraining {
    private int id;
    private int instructorId;
    private String category;
    private String description;
    private BigDecimal hourlyRate;
    private boolean archived;
    private Timestamp createdAt;

    // Joined display fields
    private String instructorName;

    public PersonalTraining() {
    }

    public PersonalTraining(int instructorId, String category, String description, BigDecimal hourlyRate) {
        this.instructorId = instructorId;
        this.category = category;
        this.description = description;
        this.hourlyRate = hourlyRate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInstructorId() { return instructorId; }
    public void setInstructorId(int instructorId) { this.instructorId = instructorId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
}