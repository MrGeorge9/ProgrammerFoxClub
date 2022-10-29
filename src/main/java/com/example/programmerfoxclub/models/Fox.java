package com.example.programmerfoxclub.models;
import java.util.ArrayList;
import java.util.List;

public class Fox {

    private Integer id;
    private String name;
    private List<String> tricks;
    private String food;
    private String drink;
    private int foodAmount;
    private int drinkAmount;
    public List<String> actionHistory = new ArrayList<>();
    public List<String> latestActionHistory = new ArrayList<>();

    public Fox(String name, String food, String drink, int foodAmount, int drinkAmount) {
        this.name = name;
        this.food = food;
        this.drink = drink;
        this.foodAmount = foodAmount;
        this.drinkAmount = drinkAmount;
    }

    public String getName() {
        return name;
    }
    public List<String> getTricks() {
        return tricks;
    }
    public void setTricks(List<String> tricks) {
        this.tricks = tricks;
    }
    public String getFood() {
        return food;
    }
    public void setFood(String food) {
        this.food = food;
    }
    public String getDrink() {
        return drink;
    }
    public void setDrink(String drink) {
        this.drink = drink;
    }
    public int getFoodAmount() {
        return foodAmount;
    }
    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }
    public int getDrinkAmount() {
        return drinkAmount;
    }
    public void setDrinkAmount(int drinkAmount) {
        this.drinkAmount = drinkAmount;
    }
}
