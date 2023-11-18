package com.example.cs208_assignment4;

import java.util.Random;

public class Player extends Entity {
    private int score;

    public Player(String name, int armor, int health,  int damagePerTurn) {
        super(name, armor, health, damagePerTurn);
        this.score = 0;
    }

    @Override
    public int attack(int rolledDice, Entity enemy) {
        this.score += rolledDice;
        if (enemy.armor > 0){
            enemy.armor -= rolledDice;

            if (enemy.armor < 0){
                enemy.health += enemy.armor;
            }
        }

        else{
            enemy.health -= rolledDice;

            // If the enemy is defeated
            if (enemy.health <= 0){
                enemy.setAliveStatus(false);
            }
        }

        return rolledDice;
    }

    public void setDamagePerTurn(int newDamage) {
        this.damagePerTurn = newDamage;
    }

    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(damagePerTurn) + 1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
