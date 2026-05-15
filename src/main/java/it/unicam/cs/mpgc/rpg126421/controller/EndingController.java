package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller della schermata finale.
 * Mostra il riepilogo della partita e il finale ottenuto.
 */
public class EndingController {

    @FXML private Label endingTitleLabel;
    @FXML private TextArea endingSummaryArea;
    @FXML private Label finalWoolongLabel;
    @FXML private Label finalMoraleLabel;
    @FXML private Button restartButton;

    /**
     * Chiamato da GameController con la sessione completata.
     */
    public void initEnding(GameSession session) {
        String finale = session.getWorldState().getFlag("finale");

        if ("honest".equals(finale)) {
            endingTitleLabel.setText("See You, Space Cowboy...");
            endingSummaryArea.setText(
                    "Hai fatto le scelte difficili. La tua crew ti rispetta. " +
                            "I woolong scarseggiano, ma sai chi sei.\n\n" +
                            "Forse un giorno tornerai tra le stelle."
            );
        } else {
            endingTitleLabel.setText("3, 2, 1 — Let's Jam.");
            endingSummaryArea.setText(
                    "Il frigo è pieno e la Bebop vola. " +
                            "Hai lasciato qualcosa per strada, ma chi non lo fa?\n\n" +
                            "Ci sono ancora taglie là fuori. Sempre."
            );
        }

        finalWoolongLabel.setText("Woolong finali: ₩ " + session.getFinance().getWoolong());
        finalMoraleLabel.setText("Morale finale: " + session.getCaptain().getMorale());
    }

    @FXML
    private void onRestart() {
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }
}