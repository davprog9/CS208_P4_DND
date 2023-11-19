package com.example.cs208_assignment4;

import java.util.Random;

/**
 * This is the Player class which extends the Entity class. Within the constructor, every player has a name,
 * starting armor, starting health, damage per turn, and a score which starts at 0. The attack method uses the rollDice
 * method, which creates a Random number with its maximum being the Players damagePerTurn, and subtracts that number
 * from the Enemies health or armor. It also adds that number to the players score.
 * @author David Arzumanyan, Victor Serra
 */
public class Player extends Entity {
    private int score;

    /**
     * Default constructor
     * @author Victor Serra
     */
    public Player(){
        super("player",1,1,1);
        this.score = 0;
    }

    /**
     * Main constructor
     * @param name defines the name of the player
     * @param armor defines the armor of the player
     * @param health defines the health of the player
     * @param damagePerTurn defines damage per turn name of the player
     * @author Victor Serra
     */
    public Player(String name, int armor, int health,  int damagePerTurn) {
        super(name, armor, health, damagePerTurn);
        this.score = 0;
    }

    /**
     * Method implements the attack action of the Player object
     * @param rolledDice defines the rolled dice amount
     * @param enemy defines the enemy to attack
     * @return Returns a data type int - the amount of the damage
     * @author Victor Serra
     */
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

    /**
     * Setter for damage per turn
     * @param newDamage defines the new damage range for the player
     * @author Victor Serra
     */
    public void setDamagePerTurn(int newDamage) {
        this.damagePerTurn = newDamage;
    }

    /**
     * Method rolls the dice for the player
     * and gets a random number up to the damagePerTurn range
     * @return Method returns a random amount of damage to be dealt
     * @author Victor Serra
     */
    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(damagePerTurn) + 1;
    }

    /**
     * Getter for the score
     * @return Returns a data type int.
     * @author Victor Serra
     */
    public int getScore() {
        return score;
    }

    /**
     * equals method for Player object
     * @param obj defines the object to be compared with the player
     * @return Returns a data type boolean
     * @author David Arzumanyan
     */
    public boolean equals(Entity obj){
        if (obj instanceof Player){
            if (this.hashCode() == obj.hashCode()){
                if (this.health == obj.health && this.armor == obj.armor && this.damagePerTurn == obj.damagePerTurn && this.score == ((Player) obj).getScore()){
                    if (this.name.equals(obj.name)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
