package com.example.cs208_assignment4;

import java.util.Random;

class Enemy extends Entity {
    public Enemy(String name, int armor, int health, int damagePerTurn) {
        super(name, armor, health, damagePerTurn);
    }

    @Override
    public int attack(int rolledDice, Entity enemy) {

        if (this.armor > 0) {
            enemy.armor -= rolledDice;

            if (enemy.armor < 0) {
                enemy.health += this.armor;
            }
        } else {
            enemy.health -= rolledDice;
        }

        return rolledDice;
    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(damagePerTurn) + 1;
    }
}