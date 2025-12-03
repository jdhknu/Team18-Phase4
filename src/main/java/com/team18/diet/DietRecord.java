package com.team18.diet;

import java.sql.Date;

public class DietRecord {

    private String userId;      
    private Date recordDate;   
    private Long dietItem;     

    private Double totalKcal;   
    private Double totalCarbs; 
    private Double totalProtein;
    private Double totalFat;    
    private Double totalSugar; 
    private Double totalSodium; 

 

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Long getDietItem() {
        return dietItem;
    }

    public void setDietItem(Long dietItem) {
        this.dietItem = dietItem;
    }

    public Double getTotalKcal() {
        return totalKcal;
    }

    public void setTotalKcal(Double totalKcal) {
        this.totalKcal = totalKcal;
    }

    public Double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Double getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(Double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }

    public Double getTotalSugar() {
        return totalSugar;
    }

    public void setTotalSugar(Double totalSugar) {
        this.totalSugar = totalSugar;
    }

    public Double getTotalSodium() {
        return totalSodium;
    }

    public void setTotalSodium(Double totalSodium) {
        this.totalSodium = totalSodium;
    }
}