package it.unicam.cs.mpgc.rpg126421;

import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point del gioco Cowboy BebOOP.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cowboy BebOOP — 3, 2, 1, Let's Jam");
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);

        SceneManager.init(primaryStage);
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }

    public static void main(String[] args) {
        launch(args);
    }
}