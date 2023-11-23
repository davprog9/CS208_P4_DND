// David Arzumanyan, Victor Serra, Christopher Duran
package com.example.cs208_assignment4;

/**
 * Entity class that player and enemy inherit from, has an isAlive and setAliveStatus which is used whenever an Entity
 * dies. And the hashCode method, which goes through each char in the entities name and the using its ascii
 * value, sums it together for Entities hashCode.
 * @author Christopher Duran
 */
public abstract class Entity implements Actions {
    protected int armor;
    protected int health;
    protected int damagePerTurn;

    protected String name;
    protected boolean aliveStatus;

    /**
     * Main and default constructor for the Entity
     * @param name define the name of the entity
     * @param armor define the armor of the entity
     * @param health define the health of the entity
     * @param damagePerTurn define the damage per turn of the entity
     * @author Christopher Duran
     */
    public Entity(String name, int armor, int health, int damagePerTurn) {
        this.name = name;
        this.armor = armor;
        this.health = health;
        this.damagePerTurn = damagePerTurn;
        this.aliveStatus = true;
    }

    /**
     * Getter for the alive status
     * @return Returns a data type boolean
     * @author Christopher Duran
     */
    public boolean isAlive() {
        return this.aliveStatus;
    }

    /**
     * Setter for the alive status
     * @param aliveStatus defines the status
     * @author Christopher Duran
     */
    public void setAliveStatus(boolean aliveStatus) {
        this.aliveStatus = aliveStatus;
    }

    /**
     * Method includes a unique implementation of a hashcode
     * @return Returns a data type int
     * @author David Arzumanyan
     */
    @Override
    public int hashCode() {
        String name = this.name;

        int hashCode = 0;

        for (int i = 0; i < name.length(); i++){
            char character = name.charAt(i);
            int asciiValue = (int) character;
            hashCode += asciiValue;
        }

        hashCode *= hashCode;

        return hashCode;
    }
}

