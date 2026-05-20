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
    @FXML private TextArea logArea;


    /**
     * Chiamato da GameController con la sessione completata.
     */
    public void initEnding(GameSession session) {
        String finale = session.getWorldState().getFlag("finale");
        if (finale == null) finale = "unknown";

        switch (finale) {
            case "honest" -> {
                endingTitleLabel.setText("See You, Space Cowboy...");
                endingSummaryArea.setText(
                        "Hai trasmesso tutto. Il mondo sa.\n" +
                                "La Helix Corporation è finita. Aaron Morrow è stato vendicato.\n\n" +
                                "Marcus guarda fuori dal finestrino senza dire nulla.\n" +
                                "Non ne ha bisogno."
                );
            }
            case "delivered" -> {
                endingTitleLabel.setText("Justice, More or Less.");
                endingSummaryArea.setText(
                        "Kessler è in custodia ISSP. Per quanto durerà?\n" +
                                "Marcus sembra quasi in pace. Quasi.\n\n" +
                                "La Blue Mantis riparte. C'è sempre un'altra taglia."
                );
            }
            case "destroyed" -> {
                endingTitleLabel.setText("Ashes and Static.");
                endingSummaryArea.setText(
                        "I server Helix sono cenere.\n" +
                                "Nessuna prova, nessun processo. Ma il sistema è morto.\n\n" +
                                "Nyx sparisce nella notte. Come sempre."
                );
            }
            default -> {
                endingTitleLabel.setText("3, 2, 1 — Let's Jam.");
                endingSummaryArea.setText(
                        "Hai accettato l'offerta. La Blue Mantis è piena di carburante.\n" +
                                "Marcus non ti guarda più negli occhi.\n\n" +
                                "Ma i woolong non mentono."
                );
            }
        }

        finalWoolongLabel.setText("Woolong finali: ₩ " + session.getFinance().getWoolong());
        finalMoraleLabel.setText("Morale finale: " + session.getCaptain().getMorale());

        // mostra log narrativo
        logArea.setText(session.getNarrativeLog().getSummary());
    }

    @FXML
    private void onRestart() {
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }
}