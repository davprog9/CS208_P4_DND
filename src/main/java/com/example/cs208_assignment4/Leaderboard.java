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
}

/*
*
 public class Leaderboard {

 private Hashtable<Integer, Integer> damageTable;

 public Leaderboard() {
 damageTable = new Hashtable<>();
 }

 public void addDamage (Entity entity, int damage) {
 int entityCode = entity.hashCode();
 damageTable.put(entityCode, damageTable.getOrDefault(entityCode, 0) + damage);
 }

 public int getDamage(Entity entity) {
 return damageTable.getOrDefault(entity.hashCode(), 0);

 }

 public void reset() {
 damageTable.clear();
 }

 public void displayLeaderboard(Controller controller) {
 damageTable.entrySet().stream()
 .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
 .forEach(entry -> {
 Entity entity = controller.findEntityByHashCode(entry.getKey());
 if (entity != null) {
 System.out.println(entity.name + ": " + entry.getValue() + " damage");
 } else {
 System.out.println("Unknown Entity: " + entry.getValue() + " damage");
 }
 });
 }

 }
 */