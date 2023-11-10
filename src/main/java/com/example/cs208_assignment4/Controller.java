package com.example.cs208_assignment4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

    @FXML
    private Label player_name;

    @FXML
    private Label player_health;

    @FXML
    private Label player_armor;

    @FXML
    private Label enemy_name;

    @FXML
    private Label enemy_health;

    @FXML
    private Label enemy_armor;

    @FXML
    private Label rolled_number;

    @FXML
    private TextArea textArea;

    private LinkedList<Player> playerList;
    private Player current_player;

    private Enemy enemy;

    private int welcomeStatus;

    private int entity_turn;


    /**
     * Main constructor
     * Initializes list of players and one enemy object
     * @author David Arzumanyan
     */
    public Controller() {
        this.playerList = new LinkedList<Player>();

        // Adding players
        this.playerList.add(new Player("David", 100,100));
        this.playerList.add(new Player("Victor", 100,100));
        this.playerList.add(new Player("Christopher", 100,100));

        // Setting the current player and the enemy
        this.current_player = this.playerList.getFirst();
        this.enemy = new Enemy("Enemy 1", 100, 100);

        this.welcomeStatus = 0;
        this.entity_turn = 0;
    }

    /**
     * Method initializes the GUI components before
     * running the application to avoid any NullExceptions
     * @author David Arzumanyan
     */
    public void initialize(){
        // Setting the text area as non-editable
        this.textArea.setEditable(false);

        // Initialize labels and set their text
        this.player_name.setText("Player: " + this.current_player.name);
        this.player_health.setText("Health: " + this.current_player.health);
        this.player_armor.setText("Armor: " + this.current_player.armor);

        this.enemy_name.setText("Enemy: " + this.enemy.name);
        this.enemy_health.setText("Health: " + this.enemy.health);
        this.enemy_armor.setText("Armor: " + this.enemy.armor);

    }

    @FXML
    private void onRollButtonClick(ActionEvent event){
        if (this.entity_turn == 0){ // If 0 means it's player's turn, else enemy's
            rollingDice(this.current_player);
            this.current_player = this.playerList.get(2);
        }
    } // TODO: Continue writing the logic
    public void logic(){
        while(this.playerList.size() != 0){
            this.textArea.appendText("Player " + this.current_player.name + " starts rolling.");

        }
    }
    /**
     * Method starts the game and displays a prompt message
     * about playing players and the enemy
     * @author David Arzumanyan
     */
    @FXML
    public void startGame(){
        if (this.welcomeStatus == 0){
            // Displaying the first message - players and enemy information
            this.textArea.appendText("Welcome, the game has started!\n");
            this.textArea.appendText("Below listed players are playing against the " + this.enemy.name + "\n");
            for (Player player : this.playerList){
                this.textArea.appendText("Player: " + player.name + "\n");
            }
            this.welcomeStatus += 1;
        }
        else{
            this.textArea.appendText("The game is already in progress, playing players are `\n");
            for (Player player : this.playerList){
                this.textArea.appendText("Player: " + player.name + "\n");
            }
        }
    }

    /**
     * Method defines the entity turn first, rolls the dice and attacks its enemy
     * @param currEntity defines the playing entity, can be of object Player or Enemy
     * @author David Arzumanyan
     */
    @FXML
    public void rollingDice(Entity currEntity){
        // If the entity is of Player object
        if (currEntity instanceof Player){
            this.textArea.appendText("Player " + currEntity.name + " is rolling the dice\n");

            int damage = currEntity.rollDice();
            this.rolled_number.setText(String.valueOf(damage));
            this.rolled_number.setVisible(true);

            this.textArea.appendText("Player " + currEntity.name + " got the number " + damage + "\n");
            this.textArea.appendText("Enemy got damaged by " + damage + " points from player " + currEntity.name + "\n");

            // Attacking the enemy
            currEntity.attack(damage, this.enemy);

        }

        // If the entity is of Enemy object
        else if (currEntity instanceof Enemy){
            this.textArea.appendText("Enemy " + currEntity.name + " is rolling the dice\n");

            int damage = currEntity.rollDice();
            this.rolled_number.setText(String.valueOf(damage));
            this.rolled_number.setVisible(true);

            this.textArea.appendText("Enemy " + currEntity.name + " got the number " + damage + "\n");
            this.textArea.appendText("Player " + this.current_player +  " got damaged by " + damage + " points from " + currEntity.name + "\n");

            // Attacking the player
            currEntity.attack(damage, this.current_player);
        }
    }

