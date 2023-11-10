package com.example.cs208_assignment4;

import java.util.Random;

public class Player extends Entity {
    public Player(String name, int armor, int health) {
        super(name, armor, health);
    }

    @Override
    public int attack(int rolledDice, Entity enemy) {

        if (enemy.armor > 0){
            enemy.armor -= rolledDice;

            if (enemy.armor < 0){
                enemy.health += this.armor;
            }
        }

        else{
            enemy.health -= rolledDice;
        }

        return rolledDice;

        // OLD IMPLEMENTATION
        /*int damage = rolledDice - this.armor;
        if (damage < 0) {
            damage = 0;
        }

        this.health -= damage;
        if (this.health <= 0) {
            this.aliveStatus = false;
        }

        return damage;*/
    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(30) + 1;
    }
}
