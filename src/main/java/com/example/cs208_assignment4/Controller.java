package com.example.cs208_assignment4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.*;

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
    private int levelNum;
    private final String[] enemyNames = new String[]{   //List of unique enemy names
            "Skeleton",
            "Goblin",
            "Troll",
            "Giant",
            "Dragon"
    };
    private Iterator<Player> iterator;

    private HashMap<Integer, Entity> entityMap;

    private Leaderboard leaderboard;


    /**
     * Main constructor
     * Initializes list of players and one enemy object
     * @author David Arzumanyan
     */
    public Controller() {
        this.playerList = new LinkedList<Player>();
        entityMap = new HashMap<>();
        leaderboard = new Leaderboard();
        //initializes level counter
        this.levelNum = 1;

        // Adding players

        this.playerList.add(new Player("David", 100, 100, 30));
        this.playerList.add(new Player("Victor", 100, 100, 30));
        this.playerList.add(new Player("Christopher", 100, 100, 30));
        for (Player player : playerList) {
            registerEntity(player);
        }

        // Setting the current player and the enemy
        this.current_player = this.playerList.getFirst();
        this.enemy = new Enemy(enemyNames[0], 50, 100, 55);

        this.welcomeStatus = 0;
        this.entity_turn = 0;

        this.iterator = this.playerList.iterator();
    }

    // Call this method whenever a new Entity is created
    public void registerEntity(Entity entity) {
        entityMap.put(entity.hashCode(), entity);
    }

    // Add a method to find an Entity by hashCode
    public Entity findEntityByHashCode(int hashCode) {
        return entityMap.get(hashCode);
    }


    /**
     * Method initializes the GUI components before
     * running the application to avoid any NullExceptions
     * @author David Arzumanyan
     */
    public void initialize() {
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
    private void onRollButtonClick(ActionEvent event) {
        if (this.welcomeStatus == 0) {
            this.textArea.appendText("Start the game in order to be able to roll a dice!\n");
        }
        else {
            if (this.entity_turn == 0) { // If 0 means it's player's turn, else enemy's
                if (this.iterator.hasNext()) {
                    this.current_player = iterator.next();
                    this.player_name.setText("Player: " + this.current_player.name);
                    this.player_armor.setText("Armor: " + this.current_player.armor);
                    this.player_health.setText("Health: " + this.current_player.health);
                    rollingDice(this.current_player);
                }

                // Enemy attacking, since the iterator went through all players and each player already attacked
                else {
                    rollingDice(this.enemy);

                    // Resetting the iterator to the first element of the list
                    // In order to let players attack again
                    this.iterator = this.playerList.iterator();
                }
            }
        }
    } // TODO: Continue writing the logic

    public void logic() {
        while (this.playerList.size() != 0) {
            this.textArea.appendText("Player " + this.current_player.name + " starts rolling.\n");
        }
    }

    /**
     * Method starts the game and displays a prompt message
     * about playing players and the enemy
     * @author David Arzumanyan
     */
    @FXML
    public void startGame() {
        if (this.welcomeStatus == 0) {
            // Displaying the first message - players and enemy information
            this.textArea.appendText("Welcome, the game has started!\n");
            this.textArea.appendText("Below listed players are playing against the " + this.enemy.name + "\n");
            for (Player player : this.playerList) {
                this.textArea.appendText("Player: " + player.name + "\n");
            }
            this.welcomeStatus += 1;
        } else {
            this.textArea.appendText("The game is already in progress, playing players are `\n");
            for (Player player : this.playerList) {
                this.textArea.appendText("Player: " + player.name + "\n");
            }
        }
    }

    /**
     * This method is run when the Enemy health reaches 0.
     * It increments the level counter, {@code levelNum}, and sets enemy's name, damage and armor
     * to a value related to this level counter. Once {@code maxLevels} is reached, the game is won,
     * it will display the leaderboard.
     * @author Victor Serra
     */
    public void nextLevel(){
        int maxLevels = 5;
        if (levelNum != maxLevels) {
            //increment level counter
            this.levelNum += 1;
            this.textArea.appendText("  -You have reached Level " + levelNum + "\n");
            //Increase Enemy Stats
            this.enemy_name.setText(enemyNames[levelNum-1]);
            this.enemy.health = 100 * levelNum;
            this.enemy.armor = 50 * levelNum;
        }else{
            gameOver();
        }
    }

    /**
     * This method will be called when all levels are beaten, or if all players die.
     * It sorts the {@code playerList} by the score parameter, and appends at the end
     * of the game in {@code textArea}.
     * @author Victor Serra
     */
    public void gameOver(){
        // TODO: Implement Leaderboard here (This is for the time being)
        //sets each player score param to damage dealt
        this.textArea.clear();
        for (Player p: playerList){
            p.setScore(leaderboard.getDamage(p));
        }
        //sorts and appends ordered players score at the end of the game
        playerList.sort(Comparator.comparingInt(Player::getScore));
        int j = 1;
        for (int i = playerList.size()-1; i >= 0; i--) {
            this.textArea.appendText(j + ") Player " + playerList.get(i).name + " Dealt " + leaderboard.getDamage(playerList.get(i)) + " Damage!\n");
            j++;
        }
        //maybe restart game here
        //this.welcomeStatus = 0;
    }

    /**
     * Method defines the entity turn first, rolls the dice and attacks its enemy
     * @param currEntity defines the playing entity, can be of object Player or Enemy
     * @author David Arzumanyan
     */
    @FXML
    public void rollingDice(Entity currEntity) {
        // If the entity is of Player object
        if (currEntity instanceof Player) {
            this.textArea.appendText("Player " + currEntity.name + " is rolling the dice\n");

            int damage = currEntity.rollDice();
            this.rolled_number.setText(String.valueOf(damage));
            this.rolled_number.setVisible(true);
            leaderboard.addDamage(currEntity, damage);

            this.textArea.appendText("Player " + currEntity.name + " got the number " + damage + "\n");
            this.textArea.appendText("Enemy got damaged by " + damage + " points from player " + currEntity.name + "\n");

            // Attacking the enemy
            currEntity.attack(damage, this.enemy);

            if (this.enemy.armor <= 0){
                this.enemy_armor.setText("Armor: 0");
            }
            else{
                this.enemy_armor.setText("Armor: " + this.enemy.armor);
            }

            if (this.enemy.health <= 0){
                this.enemy_health.setText("Health: 0");
                this.textArea.appendText("Enemy: " + this.enemy.name + " defeated!\n");
                nextLevel();
            }
            else{
                this.enemy_health.setText("Health: " + this.enemy.health);
            }

        }

        // If the entity is of Enemy object
        else if (currEntity instanceof Enemy) {
            this.textArea.appendText("Enemy " + currEntity.name + " is rolling the dice\n");

            int damage = currEntity.rollDice();
            this.rolled_number.setText(String.valueOf(damage));
            this.rolled_number.setVisible(true);

            this.textArea.appendText("Enemy " + currEntity.name + " got the number " + damage + "\n");

            // Attacking the player
            Random random = new Random();
            Player random_player = this.playerList.get(random.nextInt(3));
            currEntity.attack(damage, random_player);

            Iterator<Player> player_iterator = this.playerList.iterator();

            // Updating the health and armor labels for damaged player
            if (random_player.equals(this.current_player)){
                this.player_health.setText("Health: " + this.current_player.health);
                this.player_armor.setText("Armor: " + this.current_player.armor);
            }


            this.textArea.appendText("Player " + random_player.name + " got damaged by " + damage + " points from " + currEntity.name + "\n");
        }
    }
}

// TODO: fix the player's health and armor texts

