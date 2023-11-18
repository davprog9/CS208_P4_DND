package com.example.cs208_assignment4;

import java.util.Hashtable;

public class Leaderboard {

    private Hashtable scoreTable; // Table for storing scores

    public Leaderboard() {
        scoreTable = new Hashtable(10); // Initialize the score table with initial capacity
    }

    public void updateScore(Entity player, int score) {
        int playerCode = player.hashCode();
        scoreTable.put(playerCode, score);
    }

    public int getScore(Entity player) {
        int playerCode = player.hashCode();
        return (int) scoreTable.get(playerCode);
    }

    public Hashtable getScoreTable() {
        return scoreTable;
    }
}