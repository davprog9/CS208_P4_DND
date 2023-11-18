package com.example.cs208_assignment4;

import java.util.Map;

public class Leaderboard {

    private Hashtable damageTable;

    public Leaderboard() {
        damageTable = new Hashtable(10);
    }

    public void addDamage (Player entity, int damage) {
        int entityCode = entity.hashCode();
        damageTable.put(entityCode, entity.getScore());
    }

    public int getDamage(Player player) {
        return player.getScore();
    }

}
