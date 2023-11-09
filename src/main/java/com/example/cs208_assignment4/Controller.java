package com.example.cs208_assignment4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    public Controller() {
    }

    @FXML
    public void func() {
        System.out.println("Hello");
    }
}
