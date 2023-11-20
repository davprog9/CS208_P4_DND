// David Arzumanyan, Victor Serra, Christopher Duran
package com.example.cs208_assignment4;

/**
 * Class contains the leaderboard of players
 * <p>Leaderboard is being defined with help of a custom implemented hashtable.
 * Players are being put into leaderboard hashtable based on their unique hashcode.
 * @author Christopher Duran
 */
public class Leaderboard {

    private Hashtable scoreTable; // Table for storing scores

    public Leaderboard() {
        scoreTable = new Hashtable(10); // Initialize the score table with initial capacity
    }

    /**
     * Method to update the scores of players
     * @param player defines the player
     * @param score defines the score
     * @author Christopher Duran
     */
    public void updateScore(Entity player, int score) {
        int playerCode = player.hashCode();
        scoreTable.put(playerCode, score);
    }

    /**
     * Getter for player's score
     * @param player defines the player
     * @return Returns a data type int
     * @author Christopher Duran
     */
    public int getScore(Entity player) {
        int playerCode = player.hashCode();
        return (int) scoreTable.get(playerCode);
    }
}