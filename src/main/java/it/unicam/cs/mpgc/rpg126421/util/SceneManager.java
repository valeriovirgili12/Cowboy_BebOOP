package it.unicam.cs.mpgc.rpg126421.util;

import it.unicam.cs.mpgc.rpg126421.service.AudioService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;
    private static final AudioService audioService = new AudioService();

    public static void init(Stage stage) {
        primaryStage = stage;
        primaryStage.setMaximized(true);
    }

    public static void switchTo(AppScene appScene) {
        applyScene(loadScene(appScene));
    }

    public static <T> T switchToAndGetController(AppScene appScene) {
        FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource(appScene.getFxmlPath())
        );
        try {
            applyScene(buildScene(loader));
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la scena: " + appScene.getFxmlPath(), e);
        }
        return loader.getController();
    }

    // --- privati ---

    private static Scene loadScene(AppScene appScene) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(appScene.getFxmlPath())
            );
            return buildScene(loader);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare la scena: " + appScene.getFxmlPath(), e);
        }
    }

    private static Scene buildScene(FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.BLACK);
        return scene;
    }

    private static void applyScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getStage() { return primaryStage; }
    public static AudioService getAudio() { return audioService; }
}