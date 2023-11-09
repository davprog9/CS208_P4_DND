package com.example.cs208_assignment4;

public class Level {
    private final Entity[] subjects;
    private final Player[] players;
    private int  numberOfPlayers, currPlayerTurn, currLevelTurn;


    public Level (int numPlayers){
        this.numberOfPlayers = numPlayers;
        players = new Player[numPlayers];
        subjects = new Entity[numPlayers+1];
        currPlayerTurn = 0;
        currLevelTurn = 0;
    }
    public void addPlayer(Player newPlayer){
        players[currPlayerTurn] = newPlayer;
        currPlayerTurn++;
    }
    public Entity[] getEntities(){
        if (numberOfPlayers > 0) System.arraycopy(players, 0, subjects, 0, numberOfPlayers);
        return subjects;
    }
    public Player getCurrPlayer(){
        return players[currPlayerTurn];
    }
}
