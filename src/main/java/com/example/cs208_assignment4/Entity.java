package com.example.cs208_assignment4;

/**
 * Class represents an Entity object
 * which has different unique parameters
 * @author Christopher Duran
 */
public abstract class Entity implements Actions {
    protected int armor;
    protected int health;
    protected int damagePerTurn;

    protected String name;
    protected boolean aliveStatus;

    public Entity(String name, int armor, int health, int damagePerTurn) {
        this.name = name;
        this.armor = armor;
        this.health = health;
        this.damagePerTurn = damagePerTurn;
        this.aliveStatus = true;
    }

    public boolean isAlive() {
        return this.aliveStatus;
    }

    public void setAliveStatus(boolean aliveStatus) {
        this.aliveStatus = aliveStatus;
    }
    // public void addEnemy(int level, Entity enemy) {this.enemies.put(level, enemy);}

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

        return hashCode;

    }
}

