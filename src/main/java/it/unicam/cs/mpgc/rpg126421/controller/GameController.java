package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.episode.Choice;
import it.unicam.cs.mpgc.rpg126421.model.episode.Scene;
import it.unicam.cs.mpgc.rpg126421.service.GameService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Controller della schermata di gioco principale.
 * Mostra testo narrativo e scelte disponibili.
 * Delega tutta la logica a GameService.
 */
public class GameController {

    @FXML private Label episodeTitleLabel;
    @FXML private Label captainNameLabel;
    @FXML private Label woolongLabel;
    @FXML private Label moraleLabel;
    @FXML private TextArea narrativeArea;
    @FXML private VBox choicesBox;

    private GameService gameService;

    /**
     * Chiamato da CharacterCreationController dopo il caricamento dell'FXML.
     */
    public void initSession(GameService gameService) {
        this.gameService = gameService;
        updateHUD();
        startNextEpisode();
    }

    private void updateHUD() {
        var session = gameService.getSession();
        captainNameLabel.setText(session.getCaptain().getName());
        woolongLabel.setText("₩ " + session.getFinance().getWoolong());
        moraleLabel.setText("Morale: " + session.getCaptain().getMorale());
    }

    private void startNextEpisode() {
        gameService.startNextEpisode().ifPresentOrElse(
                episode -> {
                    episodeTitleLabel.setText(episode.getTitle());
                    showCurrentScene();
                },
                this::showEnding
        );
    }

    private void showCurrentScene() {
        gameService.getCurrentScene().ifPresent(this::renderScene);
    }

    private void renderScene(Scene scene) {
        narrativeArea.setText(scene.getNarrativeText());
        choicesBox.getChildren().clear();

        List<Choice> available = gameService.getAvailableChoices(scene);

        for (Choice choice : scene.getChoices()) {
            Button btn = new Button(choice.getText());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);

            if (available.contains(choice)) {
                btn.setOnAction(e -> onChoiceSelected(scene, choice));
            } else {
                btn.setDisable(true);
                // mostra hint del requisito non soddisfatto
                choice.getRequirement().ifPresent(r ->
                        btn.setText(choice.getText() + "\n[" + r.getHint() + "]")
                );
            }
            choicesBox.getChildren().add(btn);
        }
    }

    private void onChoiceSelected(Scene scene, Choice choice) {
        gameService.resolveScene(scene, choice);
        narrativeArea.setText(choice.getOutcome().getNarrativeText());
        choicesBox.getChildren().clear();
        updateHUD();

        // bottone per proseguire
        Button nextBtn = new Button("Continua →");
        nextBtn.setOnAction(e -> advance());
        choicesBox.getChildren().add(nextBtn);
    }

    private void advance() {
        if (gameService.tryCompleteCurrentEpisode()) {
            startNextEpisode();
        } else {
            showCurrentScene();
        }
        updateHUD();
    }

    private void showEnding() {
        EndingController endingController =
                SceneManager.switchToAndGetController(AppScene.ENDING);
        endingController.initEnding(gameService.getSession());
    }
}