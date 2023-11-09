package com.example.cs208_assignment4;

import java.util.Hashtable;
import java.util.Random;

abstract class Entity implements Actions {
    protected int armor;
    protected int health;

    protected String name;
    protected boolean aliveStatus;
    protected int levelCount;
    protected Hashtable<Integer, Entity> enemies = new Hashtable();

    public Entity(String name, int armor, int health) {
        this.name = name;
        this.armor = armor;
        this.health = health;
        this.aliveStatus = true;
        this.levelCount = 0;
    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(20) + 1;
    }

    public int attack(int rolledDice) {
        int damage = rolledDice - this.armor;
        if (damage < 0) {
            damage = 0;
        }

        this.health -= damage;
        if (this.health <= 0) {
            this.aliveStatus = false;
        }

        return damage;
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

