package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller del menu principale.
 * Gestisce solo la navigazione — nessuna logica di gioco.
 */
public class MainMenuController {

    @FXML private Button newGameButton;
    @FXML private Button quitButton;

    @FXML
    private void onNewGame() {
        SceneManager.switchTo(AppScene.CHARACTER_CREATION);
    }

    @FXML
    private void onQuit() {
        SceneManager.getStage().close();
    }
}