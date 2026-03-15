package com.farmstep.dto;

public class DiagnosisRequest {

    private Long userId;
    private String experience;
    private String migration;
    private int budget;
    private String stability;
    private String independence;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMigration() {
        return migration;
    }

    public void setMigration(String migration) {
        this.migration = migration;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }

    public String getIndependence() {
        return independence;
    }

    public void setIndependence(String independence) {
        this.independence = independence;
    }
}