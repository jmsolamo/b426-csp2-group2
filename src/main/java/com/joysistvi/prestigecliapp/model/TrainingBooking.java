package com.joysistvi.prestigecliapp.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TrainingBooking {
    private int id;
    private int userId;
    private int personalTrainingId;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    private BigDecimal totalHours;
    private BigDecimal totalAmount;
    private String status;
    private boolean archived;
    private Timestamp createdAt;

    // Joined display fields
    private String memberName;
    private String memberUsername;
    private int instructorId;
    private String instructorName;
    private String category;
    private BigDecimal hourlyRate;

    public TrainingBooking() {
    }

    public TrainingBooking(int userId, int personalTrainingId, Date bookingDate, Time startTime, Time endTime,
                           BigDecimal totalHours, BigDecimal totalAmount, String status) {
        this.userId = userId;
        this.personalTrainingId = personalTrainingId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPersonalTrainingId() { return personalTrainingId; }
    public void setPersonalTrainingId(int personalTrainingId) { this.personalTrainingId = personalTrainingId; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Time getEndTime() { return endTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }

    public BigDecimal getTotalHours() { return totalHours; }
    public void setTotalHours(BigDecimal totalHours) { this.totalHours = totalHours; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

    public String getMemberUsername() { return memberUsername; }
    public void setMemberUsername(String memberUsername) { this.memberUsername = memberUsername; }

    public int getInstructorId() { return instructorId; }
    public void setInstructorId(int instructorId) { this.instructorId = instructorId; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
}