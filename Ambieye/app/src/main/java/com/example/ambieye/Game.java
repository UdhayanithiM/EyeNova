package com.example.ambieye;

public class Game {
    public String title;
    public String description;
    public String type;
    public Class<?> activityClass;

    public Game(String title, String description, String type, Class<?> activityClass) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.activityClass = activityClass;
    }
}
