package it.unicam.cs.mpgc.rpg126421;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point del gioco Cowboy BebOOP.
 * Avvia la finestra JavaFX principale.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cowboy BebOOP — 3, 2, 1, Let's Jam");
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}