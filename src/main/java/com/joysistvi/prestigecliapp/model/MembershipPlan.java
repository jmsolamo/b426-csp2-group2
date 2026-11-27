package com.joysistvi.prestigecliapp.model;

public class MembershipPlan {

    private int id;
    private String planName;
    private int durationMonths;
    private double price;

    public MembershipPlan() {}

    public MembershipPlan(int id, String planName,
                          int durationMonths, double price) {
        this.id = id;
        this.planName = planName;
        this.durationMonths = durationMonths;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Generate getters and setters
}