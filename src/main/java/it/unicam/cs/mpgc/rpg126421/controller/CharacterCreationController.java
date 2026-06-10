package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.character.Captain;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.repository.EpisodeRepository;
import it.unicam.cs.mpgc.rpg126421.service.GameService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Controller della schermata di creazione personaggio.
 * Raccoglie nome e classe, costruisce GameSession e avvia il gioco.
 */
public class CharacterCreationController {

    @FXML private TextField nameField;
    @FXML private ChoiceBox<CaptainClass> classChoiceBox;
    @FXML private Label errorLabel;
    @FXML private Label perkLabel;

    @FXML
    public void initialize() {
        classChoiceBox.getItems().addAll(CaptainClass.values());
        classChoiceBox.setValue(CaptainClass.BOUNTY_HUNTER);
        updatePerkLabel();

        // aggiorna il perk quando cambia la classe
        classChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> updatePerkLabel());
    }

    private void updatePerkLabel() {
        CaptainClass selected = classChoiceBox.getValue();
        if (selected != null) {
            perkLabel.setText("Perk: " + selected.getPerk());
        }
    }

    @FXML
    private void onStartGame() {
        String name = nameField.getText().trim();

        if (name.isBlank()) {
            errorLabel.setText("Inserisci un nome per il tuo personaggio.");
            return;
        }

        CaptainClass characterClass = classChoiceBox.getValue();
        Captain captain = new Captain(name, characterClass);
        GameSession session = new GameSession(captain);

        // carica gli episodi nella sessione
        List<Episode> episodes = new EpisodeRepository().getAllEpisodes();
        episodes.forEach(session::addEpisode);

        // passa la sessione al GameController
        GameController gameController = SceneManager.switchToAndGetController(AppScene.GAME);
        gameController.initSession(new GameService(session));
    }

    @FXML
    private void onBack() {
        SceneManager.switchTo(AppScene.MAIN_MENU);
    }
}