package com.example.nutritionapp;

public class DailyKcalModel {
    String date;

    public String getDate() {
        return date;
    }

    public int getKcal() {
        return kcal;
    }

    public DailyKcalModel(String date, int kcal) {
        this.date = date;
        this.kcal = kcal;
    }

    public DailyKcalModel() {
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    int kcal;
}
