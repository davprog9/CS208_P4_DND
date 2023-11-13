package com.example.cs208_assignment4;

import java.util.Hashtable;

public abstract class Entity implements Actions {
    protected int armor;
    protected int health;
    protected int damagePerTurn;
    protected String name;
    protected boolean aliveStatus;
    protected int levelCount;
    protected Hashtable<Integer, Entity> enemies = new Hashtable();

    public Entity(String name, int armor, int health, int damagePerTurn) {
        this.name = name;
        this.armor = armor;
        this.health = health;
        this.damagePerTurn = damagePerTurn;
        this.aliveStatus = true;
        this.levelCount = 0;
    }

    public boolean isAlive() {
        return this.aliveStatus;
    }

    public void addEnemy(int level, Entity enemy) {
        this.enemies.put(level, enemy);
    }

    public int hashCode() {
        return this.name.hashCode() ^ Integer.hashCode(this.health) ^ Integer.hashCode(this.armor);
    }
}

