package com.team18.goal;

import java.sql.Date;

public class NutritionGoal {
    private String userId;
    private double targetCalories;
    private double targetProtein;
    private double targetCarb;
    private double targetFat;
    private Date createdAt;

    public NutritionGoal() {}

    public NutritionGoal(String userId, double kcal, double protein, double carb, double fat) {
        this.userId = userId;
        this.targetCalories = kcal;
        this.targetProtein = protein;
        this.targetCarb = carb;
        this.targetFat = fat;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public double getTargetCalories() { return targetCalories; }
    public void setTargetCalories(double targetCalories) { this.targetCalories = targetCalories; }
    public double getTargetProtein() { return targetProtein; }
    public void setTargetProtein(double targetProtein) { this.targetProtein = targetProtein; }
    public double getTargetCarb() { return targetCarb; }
    public void setTargetCarb(double targetCarb) { this.targetCarb = targetCarb; }
    public double getTargetFat() { return targetFat; }
    public void setTargetFat(double targetFat) { this.targetFat = targetFat; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}