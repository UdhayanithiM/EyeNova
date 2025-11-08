package com.example.ambieye;

public class HistoryItem {
    private String dateNumber, dateMonth, gameName, gameTime, gameScore;
    private int accuracy;

    public HistoryItem(String dateNumber, String dateMonth, String gameName, String gameTime, String gameScore, int accuracy) {
        this.dateNumber = dateNumber;
        this.dateMonth = dateMonth;
        this.gameName = gameName;
        this.gameTime = gameTime;
        this.gameScore = gameScore;
        this.accuracy = accuracy;
    }

    public String getDateNumber() {
        return dateNumber;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameTime() {
        return gameTime;
    }

    public String getGameScore() {
        return gameScore;
    }

    public int getAccuracy() {
        return accuracy;
    }
}
