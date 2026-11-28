package com.joysistvi.prestigecliapp.model;

public class MembershipPlan {

    private int id;
    private String name;
    private String description;
    private double price;
    private int durationDays;
    private String inclusions;
    private Integer classLimit;
    private int isArchived;

    public MembershipPlan() {
    }

    public MembershipPlan(int id,
                          String name,
                          String description,
                          double price,
                          int durationDays,
                          String inclusions,
                          Integer classLimit,
                          int isArchived) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
        this.inclusions = inclusions;
        this.classLimit = classLimit;
        this.isArchived = isArchived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public Integer getClassLimit() {
        return classLimit;
    }

    public void setClassLimit(Integer classLimit) {
        this.classLimit = classLimit;
    }

    public int getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(int isArchived) {
        this.isArchived = isArchived;
    }
}