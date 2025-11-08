package com.example.ambieye;

public class GameHistory {
    private String gameName;
    private int score;

    public GameHistory(String gameName, int score) {
        this.gameName = gameName;
        this.score = score;
    }

    public String getGameName() {
        return gameName;
    }

    public int getScore() {
        return score;
    }
}
