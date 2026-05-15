package it.unicam.cs.mpgc.rpg126421.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Gestisce la navigazione tra le schermate del gioco.
 * Unico punto di accesso per i cambi di scena — nessun controller
 * carica direttamente un FXML.
 */
public class SceneManager {

    private static Stage primaryStage;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void switchTo(AppScene appScene) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(appScene.getFxmlPath())
            );
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la scena: " + appScene.getFxmlPath(), e);
        }
    }

    /**
     * Versione con callback per passare dati al controller dopo il caricamento.
     */
    public static <T> T switchToAndGetController(AppScene appScene) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(appScene.getFxmlPath())
            );
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la scena: " + appScene.getFxmlPath(), e);
        }
    }

    public static Stage getStage() { return primaryStage; }
}