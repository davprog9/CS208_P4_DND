// David Arzumanyan, Victor Serra, Christopher Duran
package com.example.cs208_assignment4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;

/**
 * Controller class has the logic of the game.
 * <p>Class manages the JavaFXML GUI controls
 * and defines the logic of the game.
 * @author David Arzumanyan
 * @author Victor Serra
 * @author Christopher Duran
 */
public class Controller {
    @FXML
    public Button addPlayerButton;
    @FXML
    public TextField newPlayerTextField;
    @FXML
    public Label diceLabel;
    @FXML
    public Button difficultyButton;
    @FXML
    public Label difficultyLabel;
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

    @FXML
    private Button rollDice_button;

    @FXML
    private Button startGame_button;

    private LinkedList<Player> playerList;
    private ArrayList<String> playerNameList;

    private LinkedList<Player> lobby;

    private Player current_player;

    private Enemy enemy;

    private int welcomeStatus;

    private int levelNum;

    private final String[] enemyNames = new String[]{   // List of unique enemy names
            "Skeleton",
            "Goblin",
            "Troll",
            "Giant",
            "Evil Knight",
            "Skeleton King",
            "Dragon",
            "Demi-gorgon"
    };

    private Iterator<Player> iterator;
    private Leaderboard leaderboard;
    private int playerCount = 1;
    private int maxLevels;


    /**
     * Method initializes the GUI components, playerList, lobby and leaderboard before
     * running the application to avoid any NullExceptions. Also sets starting level
     * number and initial enemy.
     * @author David Arzumanyan
     */
    public void initialize() {
        // Setting the text area as non-editable
        this.textArea.setEditable(false);
        this.newPlayerTextField.setStyle("-fx-background-color: white;");
        this.textArea.setText("^Add New Players Above^ \n");

        this.playerList = new LinkedList<Player>();
        this.playerNameList = new ArrayList<>();
        this.lobby = new LinkedList<Player>();
        this.leaderboard = new Leaderboard();

        // Initializes level counter
        this.levelNum = 1;

        // Setting the enemy
        this.enemy = new Enemy(enemyNames[0], 50, 100, 55);
        this.welcomeStatus = 0;
    }

    /**
     * Controls what happens when addPlayer button is pressed. Adds a new player with the name typed within
     * {@code newPlayerTextField} and appends text stating that it was added to {@code playerList}. If nothing
     * is typed and addPlayer is pressed, "Invalid Player Name" message is appended.
     * @param event defines the event which happens at every click on the button
     * @author Victor Serra
     */
    @FXML
    private void onAddPlayer(ActionEvent event){
        //If no name is typed or name is already chosen print "Invalid Player Name"
        if(this.newPlayerTextField.getText().equals("") || playerNameList.contains(this.newPlayerTextField.getText())){
            this.textArea.appendText("Invalid Player Name.\n");
        }else { //else add Player to list with chosen name
            this.playerList.add(new Player(this.newPlayerTextField.getText(), 100, 100, 25));
            this.playerNameList.add(this.newPlayerTextField.getText());
            this.textArea.appendText(this.newPlayerTextField.getText() + " was added to party! (Total PLayers: " + playerCount + ")\n");
            this.playerCount++;
            this.newPlayerTextField.clear();
            this.newPlayerTextField.setPromptText("New Player Name");
        }
    }

    /**
     * Controls the logic of the game
     * @param event defines the event which happens at every click on the button
     * @author David Arzumanyan
     */
    @FXML
    private void onRollButtonClick(ActionEvent event) {
        if (this.welcomeStatus == 0) {
            this.textArea.appendText("Start the game in order to be able to roll a dice!\n");
        }
        else {
            this.startGame_button.setText("Game status");
            // While there are available players
            if (this.iterator.hasNext()) {

                // Choosing the next player
                this.current_player = iterator.next();
                this.player_name.setText("Player: " + this.current_player.name);

                // Checking for player's armor
                if (this.current_player.armor <= 0) {
                    this.player_armor.setText("Armor: 0");
                }
                else {
                    this.player_armor.setText("Armor: " + this.current_player.armor);
                }

                // Checking for player's health
                if (this.current_player.health <= 0) {
                    this.player_health.setText("Health: 0");
                    this.textArea.appendText("Player: " + this.current_player.name + " defeated!\n");
                    //Once Current Enemy dies, go to next level
                    nextLevel();
                }
                else {
                    this.player_health.setText("Health: " + this.current_player.health);
                }

                // Rolling the dice
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


    /**
     * Method starts the game and displays a prompt message
     * about playing players and the enemy
     * @author David Arzumanyan
     */
    @FXML
    public void startGame() {
        //Sets the attributes for the game depending on the difficulty
        String difficulty = this.difficultyButton.getText();
        if(difficulty.equals("Easy")) { //Players start with an abundant amount of health and a lot of damage
            this.maxLevels = 3; //only 3 enemies
            for(Player p: playerList){
                p.health = 200;
                p.setDamagePerTurn(75);
            }
        } else if (difficulty.equals("Normal")) {   //Players start with a good amount of health and good damage
            this.maxLevels = 5; //5 enemies
            for(Player p: playerList){
                p.health = 125;
                p.setDamagePerTurn(35);
            }
        } else if (difficulty.equals("Hard")) {  //Players start with standard amount of health and ok damage
            this.maxLevels = 8; //8 enemies
        }
        //checks if any players have been created
        if (playerList.size()!=0) {
            //Hides initial elements
            this.newPlayerTextField.setVisible(false);
            this.addPlayerButton.setVisible(false);
            this.difficultyButton.setVisible(false);
            this.difficultyLabel.setVisible(false);

            //Show invisible elements
            this.enemy_name.setVisible(true);
            this.enemy_health.setVisible(true);
            this.enemy_armor.setVisible(true);
            this.diceLabel.setVisible(true);
            this.rolled_number.setVisible(true);
            this.player_health.setVisible(true);
            this.player_armor.setVisible(true);

            // Sets current player as first player added
            this.current_player = this.playerList.getFirst();
            this.iterator = this.playerList.iterator();

            // Initialize labels and set their text
            this.player_name.setText("Player: " + this.current_player.name);
            this.player_health.setText("Health: " + this.current_player.health);
            this.player_armor.setText("Armor: " + this.current_player.armor);

            this.enemy_name.setText("Enemy: " + this.enemy.name);
            this.enemy_health.setText("Health: " + this.enemy.health);
            this.enemy_armor.setText("Armor: " + this.enemy.armor);

            if (this.welcomeStatus == 0) {
                // Displaying the first message - players and enemy information
                this.textArea.appendText("Welcome, the game has started!\n");
                this.textArea.appendText("The game is on " + this.difficultyButton.getText() + " difficulty\n");
                this.textArea.appendText("Below listed players are playing against the " + this.enemy.name + "\n");
                for (Player player : this.playerList) {
                    this.textArea.appendText("Player: " + player.name + "\n");
                }
                this.welcomeStatus += 1;
            } else if (this.welcomeStatus == 1) {
                this.textArea.appendText("The game is already in progress, playing players are `\n");
                for (Player player : this.playerList) {
                    this.textArea.appendText("Player: " + player.name + "\n");
                }
            } else {
                this.textArea.appendText("The game is over!\n");
            }
        }else { //if no players have been created
            this.textArea.appendText("You must add players to start the game\n");
        }
    }

    /**
     * Flips though three difficulty texts on {@code difficultyButton} (Easy, Normal, Hard),
     * which is used to determine the difficulty.
     * @param event
     * @author Victor Serra
     */
    public void onDifficultyButton(ActionEvent event) {
        if(this.difficultyButton.getText().equals("Easy")){ this.difficultyButton.setText("Normal");
        } else if (this.difficultyButton.getText().equals("Normal")) { this.difficultyButton.setText("Hard");
        } else if (this.difficultyButton.getText().equals("Hard")) { this.difficultyButton.setText("Easy"); }
    }

    /**
     * This method is run when the Enemy health reaches 0.
     * It increments the level counter, {@code levelNum}, and sets enemy's name, damage and armor
     * to a value related to this level counter. Once {@code maxLevels} is reached, the game is won,
     * it will display the leaderboard.
     * @author Victor Serra
     */
    public void nextLevel(){
        if (levelNum != maxLevels) {
            //increment level counter
            this.levelNum += 1;
            this.textArea.appendText("  -You have reached Level " + levelNum + "\n");
            //Increase Enemy Stats
            this.enemy.name = enemyNames[levelNum-1];
            this.enemy_name.setText("Enemy: " + enemyNames[levelNum-1]);
            this.enemy.health = 100 * levelNum;
            this.enemy.armor = 50 * levelNum;
            this.enemy_health.setText("Health: " + enemy.health);
            this.enemy_armor.setText("Armor: " + enemy.armor);
            //increase Player stats by a small amount
            for(Player p: playerList){
                double damageCounter = p.damagePerTurn * 1.6;
                p.armor += 10;
                p.setDamagePerTurn((int) (damageCounter));
            }
        }else{
            //game over
            this.textArea.appendText("=========GAME OVER=========\nAll Enemies were defeated!\n");
            this.enemy_health.setText("Health: 0");
            this.enemy_armor.setText("Armor: 0");
            rollDice_button.setDisable(true);
            gameOver();
        }
    }

    /**
     * This method will be called when all levels are beaten, or if all players die.
     * It sorts the {@code lobby} by the score parameter of players in {@code lobby}, and appends a leaderboard
     * at the end of the game in {@code textArea}.
     * @author Victor Serra
     */
    public void gameOver() {
        //Moves remaining players in Lobby
        // Using iterator for a safe remove
        Iterator<Player> iterator2 = this.playerList.iterator();
        if (this.playerList.size() > 0) {
            while (iterator2.hasNext()) {
                Player player = iterator2.next();
                this.lobby.add(player);
                iterator2.remove(); // Removing the current player
            }
        }
        for (Player p : lobby) {
            leaderboard.updateScore(p, p.getScore());
        }
        //Sorts players in lobby by their score
        lobby.sort(Comparator.comparingInt(Player::getScore));

        //Appends ordered players score at the end of the game
        int j = 1;
        this.textArea.appendText("  -Leaderboard:\n");
        for (int i = lobby.size() - 1; i >= 0; i--) {
            this.textArea.appendText(j + ") Player " + lobby.get(i).name + " Dealt " + leaderboard.getScore(lobby.get(i)) + " Damage!\n");
            j++;
        }

        // Game status button status changer
        this.welcomeStatus += 1;
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
            Player random_player = this.playerList.get(random.nextInt(this.playerList.size()));
            currEntity.attack(damage, random_player);

            this.textArea.appendText("Player " + random_player.name + " got damaged by " + damage + " points from " + currEntity.name + "\n");

            // If the random player is the current player then update the current player labels inplace
            if (random_player.equals(this.current_player)){

                // Setting the player's armor label
                if (this.current_player.armor <= 0){
                    this.player_armor.setText("Armor: 0");
                }
                else{
                    this.player_armor.setText("Armor: " + this.current_player.armor);
                }

                // Setting the player's health label
                if (this.current_player.health <= 0){
                    this.player_health.setText("Health: 0");
                    this.textArea.appendText("  -Player: " + this.current_player.name + " defeated!\n");
                    this.lobby.add(this.current_player);
                    this.playerList.remove(this.current_player);

                    // Checking if the defeated player was the last alive player
                    if (this.playerList.size() == 0){
                        this.textArea.appendText("=========GAME OVER=========\nAll players were defeated\n");
                        gameOver();
                        this.rollDice_button.setDisable(true);
                    }
                    else{
                        nextLevel();
                    }
                }
                else{
                    this.player_health.setText("Health: " + this.current_player.health);
                }
            }
            // If the random player is not the current displayed player
            // We only check whether that player is defeated and display that
            else{
                if (random_player.health <= 0){
                    this.textArea.appendText("Player " + random_player.name + " defeated!\n");
                    this.lobby.add(random_player);
                    this.playerList.remove(random_player);
                }
            }
        }
    }

    /**
     * toString for Controller class
     * @return Returns a data type String
     * @author David Arzumanyan
     */
    @Override
    public String toString() {
        return "Controller{" +
                "player_name=" + player_name +
                ", player_health=" + player_health +
                ", player_armor=" + player_armor +
                ", enemy_name=" + enemy_name +
                ", enemy_health=" + enemy_health +
                ", enemy_armor=" + enemy_armor +
                ", rolled_number=" + rolled_number +
                ", textArea=" + textArea +
                ", rollDice_button=" + rollDice_button +
                ", startGame_button=" + startGame_button +
                ", playerList=" + playerList +
                ", lobby=" + lobby +
                ", current_player=" + current_player +
                ", enemy=" + enemy +
                ", welcomeStatus=" + welcomeStatus +
                ", levelNum=" + levelNum +
                ", enemyNames=" + Arrays.toString(enemyNames) +
                ", iterator=" + iterator +
                ", leaderboard=" + leaderboard +
                '}';
    }
}
