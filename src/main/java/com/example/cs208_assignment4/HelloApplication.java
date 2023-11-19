package com.example.cs208_assignment4;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class manages the JavaFXML stage and scene
 * and launches the game.
 * @author David Arzumanyan
 */
public class HelloApplication extends Application {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 600.0, 500.0);
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Dungeon and Dragons!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}
