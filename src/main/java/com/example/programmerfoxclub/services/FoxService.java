package com.example.programmerfoxclub.services;

import com.example.programmerfoxclub.models.Drink;
import com.example.programmerfoxclub.models.Food;
import com.example.programmerfoxclub.models.Fox;
import com.example.programmerfoxclub.models.Tricks;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FoxService {

    private static List<Fox> foxes;

    public FoxService() {
        foxes = new ArrayList<>();
    }
    public Boolean nameInDatabase (String name){
        int namesCount = 0;
        for (Fox fox:foxes) {
            if (fox.getName().equals(name)){
                namesCount++;
            }
        }
        if (namesCount==0){
            return false;
        } else return true;
    }
    public void addFox (String name){
        if (!nameInDatabase(name)){
            foxes.add(new Fox(name,Food.Burger.name(),Drink.Beer.name(),50,50));
        }
    }
    public Fox selectRightFox (String name){
        Fox rightFox = foxes.stream().filter(fox -> fox.getName().equals(name))
                .collect(Collectors.toList()).get(0);
        return rightFox;
    }
    public static List<Fox> getFoxes() {
        return foxes;
    }
    public void updateFox (String name, Food food, Drink drink) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if (food.name().equals(selectRightFox(name).getFood())!=true) {
            String foodChange = dtf.format(now) + " Food has been changed from: " + selectRightFox(name).getFood() + " to: " + food.name();
            addToActionHistory(foodChange, name);
            selectRightFox(name).setFood(food.name());
        }

        if (drink.name().equals(selectRightFox(name).getDrink())!=true) {
            String drinkChange = dtf.format(now) + " Drink has been changed from: " + selectRightFox(name).getDrink() + " to: " + drink.name();
            addToActionHistory(drinkChange, name);
            selectRightFox(name).setDrink(drink.name());
        }
    }
    public List<String> availableTricks (String name, Tricks tricks) {
        List<String> availableTricks = tricks.getAllTricks();
        if (selectRightFox(name).getTricks()==null) {
            return availableTricks;
        } else {
            for (String trick: selectRightFox(name).getTricks()) {
                for (int i = 0; i < availableTricks.size(); i++) {
                    if (trick.equals(availableTricks.get(i))) {
                        availableTricks.remove(i);
                        i--;
                    }
                }
            }
            return availableTricks;
        }
    }
    public void addTrick (String name, String trick){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if (selectRightFox(name).getTricks()==null) {
            List<String> tricks = new ArrayList<>(Arrays.asList(trick));
            String trickLearn = dtf.format(now) + " Learned to: " + trick;
            addToActionHistory(trickLearn, name);
            selectRightFox(name).setTricks(tricks);
        } else {
            List<String> tricks = new ArrayList<>(selectRightFox(name).getTricks());
            tricks.add(trick);
            String trickLearn = dtf.format(now) + " Learned to: " + trick;
            addToActionHistory(trickLearn, name);
            selectRightFox(name).setTricks(tricks);
        }
    }
    public void addToActionHistory (String text, String name){
        selectRightFox(name).actionHistory.add(text);
        selectRightFox(name).latestActionHistory.add(text);
    }
    public List<String> getActionHistory (String name){
        return selectRightFox(name).actionHistory;
    }
    public List<String> get5LatestActions (String name){
        if (selectRightFox(name).latestActionHistory.size()>5) {
            for (int i = 0; i < selectRightFox(name).latestActionHistory.size()-5; i++) {
                selectRightFox(name).latestActionHistory.remove(0);
            }
        }
        return selectRightFox(name).latestActionHistory;
    }
    public List<String> choosePicture (List<String> tricks, String name) {
        List<String> pictures = new ArrayList<>();
        for (int i = 0; i < selectRightFox(name).getTricks().size(); i++) {
            switch (selectRightFox(name).getTricks().get(i)) {
                case "Flip a coin":
                    pictures.add("flipACoin.png");
                    break;
                case "Do a barell roll":
                    pictures.add("barrel.png");
                    break;
                case "Throw a spear":
                    pictures.add("spear.jpg");
                    break;
                case "Dance":
                    pictures.add("dance.jpg");
                    break;
                case "Jump":
                    pictures.add("jump.jpg");
                    break;
                case "Fly":
                    pictures.add("fly.jpg");
                    break;
                case "Firebreath":
                    pictures.add("firebreath.png");
            }
        }
        return pictures;
    }
}
