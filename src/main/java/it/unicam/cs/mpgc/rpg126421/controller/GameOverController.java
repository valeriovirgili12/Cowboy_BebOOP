package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller della schermata di game over.
 */
public class GameOverController {

    @FXML private Label titleLabel;
    @FXML private TextArea reasonArea;
    @FXML private Label woolongLabel;

    public void initGameOver(String reason, GameSession session) {
        titleLabel.setText("GAME OVER");
        reasonArea.setText(reason);
        woolongLabel.setText("Woolong alla fine: ₩ " + session.getFinance().getWoolong());
    }

    @FXML
    private void onRestart() {
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }
}