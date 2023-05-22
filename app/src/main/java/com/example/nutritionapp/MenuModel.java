package com.example.nutritionapp;

public class MenuModel {
    public MenuModel(String repast, String recommendation) {
        this.repast = repast;
        this.recommendation = recommendation;
    }

    String repast;

    public String getRepast() {
        return repast;
    }

    public String getRecommendation() {
        return recommendation;
    }

    String recommendation;



}
