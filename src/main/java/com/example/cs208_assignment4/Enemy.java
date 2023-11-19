package com.example.cs208_assignment4;

import java.util.Random;

/**
 * This is the Enemy class which extends the Entity class. Within the constructor, every enemy has a name,
 * starting armor, starting health and damage per turn. The attack method uses the rollDice method,
 * which creates a Random number with its maximum being the Enemies damagePerTurn, and subtracts that number
 * from the Players health or armor. It also adds that number to the players score.
 * @author David Arzumanyan, Christopher Duran
 */

class Enemy extends Entity {
    public Enemy(){
        super("enemy", 1,1,1);
    }
    public Enemy(String name, int armor, int health, int damagePerTurn) {
        super(name, armor, health, damagePerTurn);
    }

    @Override
    public int attack(int rolledDice, Entity enemy) {

        if (enemy.armor > 0) {
            enemy.armor -= rolledDice;

            if (enemy.armor < 0) {
                enemy.health += enemy.armor; // Example: 100 + - 10
            }
        } else {
            enemy.health -= rolledDice;

            // If the enemy is defeated
            if (enemy.health <= 0){
                enemy.setAliveStatus(false);
            }
        }

        return rolledDice;
    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(damagePerTurn) + 1;
    }

    /**
     * toString() method
     * @return Returns a data type String
     * @author Christopher Duran
     */
    @Override
    public String toString() {
        return "Enemy{" +
                "armor=" + armor +
                ", health=" + health +
                ", damagePerTurn=" + damagePerTurn +
                ", name='" + name + '\'' +
                ", aliveStatus=" + aliveStatus +
                '}';
    }
}