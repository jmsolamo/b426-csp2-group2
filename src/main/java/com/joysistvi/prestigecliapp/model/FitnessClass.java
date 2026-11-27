package com.joysistvi.prestigecliapp.model;

public class FitnessClass {

    private int id;
    private String name;
    private String description;
    private int categoryId;
    private int duration;
    private double price;
    private int isArchived;

    public FitnessClass() {}

    public FitnessClass(int id, String name, String description,
                        int categoryId, int duration,
                        double price, int isArchived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.duration = duration;
        this.price = price;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(int isArchived) {
        this.isArchived = isArchived;
    }

    // Generate getters and setters
}