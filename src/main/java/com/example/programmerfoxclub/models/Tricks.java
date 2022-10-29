package com.example.programmerfoxclub.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tricks {
    List<String> allTricks = new ArrayList<>(
            Arrays.asList("Flip a coin", "Do a barell roll", "Throw a spear", "Dance", "Jump", "Fly", "Firebreath"));
    public List<String> getAllTricks() {
        return allTricks;
    }

}
