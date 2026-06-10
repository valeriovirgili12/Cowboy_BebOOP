package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.episode.EndingContent;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.service.AudioService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

/**
 * Controller della schermata finale.
 * Mostra il riepilogo della partita e il finale ottenuto.
 */
public class EndingController {

    @FXML private Label endingTitleLabel;
    @FXML private TextArea endingSummaryArea;
    @FXML private Label finalWoolongLabel;
    @FXML private Label finalMoraleLabel;
    @FXML private TextArea logArea;



    /**
     * Chiamato da GameController con la sessione completata.
     */
    public void initEnding(GameSession session) {
        String finale = session.getWorldState().getFlag("finale");
        if (finale == null) finale = "unknown";
        playEndingAudio(finale);
        renderEndingText(finale);
        renderStats(session);
        logArea.setText(session.getNarrativeLog().getSummary());
    }

    private void playEndingAudio(String finale) {
        boolean good = List.of("killed", "destroyed", "broadcast").contains(finale);
        SceneManager.getAudio().play(good
                ? AudioService.Track.ENDING_GOOD
                : AudioService.Track.GAME_OVER);
    }

    private void renderEndingText(String finale) {
        EndingContent content = EndingContent.forFinale(finale);
        endingTitleLabel.setText(content.title());
        endingSummaryArea.setText(content.summary());
    }

    private void renderStats(GameSession session) {
        finalWoolongLabel.setText("Woolong finali: ₩ " +
                session.getFinance().getWoolong());
        finalMoraleLabel.setText("Morale finale: " +
                session.getCaptain().getMorale());
    }

    @FXML
    private void onRestart() {
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }
}