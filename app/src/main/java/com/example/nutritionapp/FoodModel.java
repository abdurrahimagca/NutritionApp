package com.example.nutritionapp;

public class FoodModel {
    String food;
    int kcal;
    public FoodModel(){

    }
    public FoodModel(String food, int kcal) {
        this.food = food;
        this.kcal = kcal;
    }



    public String getFood() {
        return food;
    }

    public int getKcal() {
        return kcal;
    }





}
