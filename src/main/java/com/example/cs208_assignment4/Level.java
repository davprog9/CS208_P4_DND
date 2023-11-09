package com.example.cs208_assignment4;

public class Level {
    private final Entity[] subjects;
    private final Player[] players;
    private final Enemy[] enemies;
    private int numberOfPlayers;
    private int numberOfEnemies;
    private int currEnemyTurn;
    private int currPlayerTurn;
    private int currLevelTurn;

    /**
     * Main constructor
     * @param numPlayers defines the number of players
     * @param numEnemies defines the number of enemies
     * @author Victor Serra
     */
    public Level(int numPlayers, int numEnemies) {
        this.numberOfPlayers = numPlayers;
        this.numberOfEnemies = numEnemies;
        this.players = new Player[numPlayers];
        this.enemies = new Enemy[numEnemies];
        this.subjects = new Entity[numPlayers + numEnemies];
        this.currPlayerTurn = 0;
        this.currEnemyTurn = 0;
        this.currLevelTurn = 0;
    }

    public void addEnemy(Enemy newEnemy) {
        this.enemies[this.currEnemyTurn] = newEnemy;
        ++this.currEnemyTurn;
    }

    public void addPlayer(Player newPlayer) {
        this.players[this.currPlayerTurn] = newPlayer;
        ++this.currPlayerTurn;
    }

    public Entity[] getEntities() {
        if (this.numberOfPlayers > 0) {
            System.arraycopy(this.players, 0, this.subjects, 0, this.numberOfPlayers);
        }

        for(int i = this.numberOfPlayers; i < this.numberOfPlayers + this.numberOfEnemies; ++i) {
            this.subjects[i] = this.enemies[i];
        }

        return this.subjects;
    }

    public Player getCurrPlayer() {
        return this.players[this.currPlayerTurn];
    }

    public Enemy getCurrEnemy() {
        return this.enemies[this.currEnemyTurn];
    }

    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    public Enemy[] getEnemies() {
        return this.enemies;
    }
}

// TODO: Array of players attack the enemy first, and then the enemy randomly picks one player and attacks one player