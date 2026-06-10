package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.episode.Choice;
import it.unicam.cs.mpgc.rpg126421.model.episode.Outcome;
import it.unicam.cs.mpgc.rpg126421.model.episode.Scene;
import it.unicam.cs.mpgc.rpg126421.service.AudioService;
import it.unicam.cs.mpgc.rpg126421.service.GameService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import it.unicam.cs.mpgc.rpg126421.util.SpriteLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

/**
 * Controller della schermata di gioco principale.
 * Mostra testo narrativo e scelte disponibili.
 * Delega tutta la logica a GameService.
 */
public class GameController {

    // ── FXML ─────────────────────────────────────────────────────────────────
    @FXML private Label episodeTitleLabel;
    @FXML private Label captainNameLabel;
    @FXML private Label woolongLabel;
    @FXML private Label moraleLabel;
    @FXML private TextArea narrativeArea;
    @FXML private HBox choicesBox;
    @FXML private ImageView backgroundImage;
    @FXML private ImageView characterImage;
    @FXML private VBox characterPanel;

    // ── Stato ─────────────────────────────────────────────────────────────────
    private GameService gameService;
    private int currentEpisodeIndex = 0;
    private Timeline activeTimer = null;
    private Timeline typewriterTimeline = null;

    // ── Init ──────────────────────────────────────────────────────────────────

    public void initSession(GameService gameService) {
        initSession(gameService, 0);
    }

    public void initSession(GameService gameService, int episodeIndex) {
        this.gameService = gameService;
        this.currentEpisodeIndex = episodeIndex;
        updateHUD();
        SceneManager.getAudio().play(AudioService.Track.GAME);
        startNextEpisode();
    }

    // ── Navigazione episodi ───────────────────────────────────────────────────

    private void startNextEpisode() {
        if (currentEpisodeIndex == 1) {
            currentEpisodeIndex++;
            MarketController marketController =
                    SceneManager.switchToAndGetController(AppScene.MARKET);
            marketController.initMarket(gameService);
            return;
        }

        gameService.startNextEpisode().ifPresentOrElse(
                episode -> {
                    currentEpisodeIndex++;
                    episodeTitleLabel.setText(episode.getTitle());
                    showCurrentScene();
                },
                this::showEnding
        );
    }

    private void showCurrentScene() {
        gameService.getCurrentScene().ifPresent(this::renderScene);
    }

    private void advance() {
        if (gameService.tryCompleteCurrentEpisode()) {
            startNextEpisode();
        } else {
            showCurrentScene();
        }
        updateHUD();
    }

    // ── Rendering scene ───────────────────────────────────────────────────────

    private void renderScene(Scene scene) {
        updateSceneVisuals(scene);
        if (scene.isFinalScene()) {
            renderFinalScene(scene);
            return;
        }

        animateText(scene.getNarrativeText());
        choicesBox.getChildren().clear();

        List<Choice> available = gameService.getAvailableChoices(scene);

        for (Choice choice : scene.getChoices()) {
            boolean isAvailable = available.contains(choice);
            boolean willFail    = choice.willFail(gameService.getSession());

            Button btn = new Button(choice.getText());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
            btn.getStyleClass().add("button");

            if (isAvailable || willFail) {
                btn.setOnAction(e -> onChoiceSelected(scene, choice));
                if (choice.hasTimeout()) startTimer(choice, scene, btn);
            } else {
                btn.setDisable(true);
            }

            VBox container = buildChoiceContainer(btn, choice.getOutcome());
            choicesBox.getChildren().add(container);
        }
    }

    private void renderFinalScene(Scene scene) {
        animateText(scene.getNarrativeText());
        choicesBox.getChildren().clear();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(grid, javafx.scene.layout.Priority.ALWAYS);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col, new ColumnConstraints());
        grid.getColumnConstraints().get(1).setPercentWidth(50);

        int[][] positions = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        List<Choice> choices = scene.getChoices();

        for (int i = 0; i < choices.size() && i < 4; i++) {
            Choice choice = choices.get(i);

            Button btn = new Button(choice.getText());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
            btn.getStyleClass().add("button");
            btn.setOnAction(e -> onChoiceSelected(scene, choice));

            VBox card = new VBox(6);
            card.setMaxWidth(Double.MAX_VALUE);
            card.setMaxHeight(Double.MAX_VALUE);
            card.setStyle(
                    "-fx-background-color: #021a02; " +
                            "-fx-border-color:  #004d14;"+
                            "-fx-border-width: 1; -fx-padding: 12;"
            );
            GridPane.setHgrow(card, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setVgrow(card, javafx.scene.layout.Priority.ALWAYS);

            card.getChildren().addAll(btn, buildSummaryBox(choice.getOutcome()));
            grid.add(card, positions[i][0], positions[i][1]);
        }

        choicesBox.getChildren().add(grid);
    }

    // ── Scelta selezionata ────────────────────────────────────────────────────

    private void onChoiceSelected(Scene scene, Choice choice) {
        stopTimer();

        boolean failed = choice.willFail(gameService.getSession());
        gameService.resolveScene(scene, choice);

        Outcome outcome = failed && choice.getOutcome().hasFailureOutcome()
                ? choice.getOutcome().getFailureOutcome()
                : choice.getOutcome();

        String marcusReaction = gameService.getSession().getMarcus()
                .reactToChoice(extractMainFlag(choice));

        String fullText = marcusReaction.isBlank()
                ? outcome.getNarrativeText()
                : outcome.getNarrativeText() + "\n\nMarcus: " + marcusReaction;

        animateText(fullText);
        choicesBox.getChildren().clear();
        updateHUD();

        if (gameService.isGameOver()) {
            showGameOver(outcome.getNarrativeText());
            return;
        }

        Button nextBtn = new Button("[ CONTINUA → ]");
        nextBtn.getStyleClass().add("button");
        nextBtn.setOnAction(e -> advance());
        choicesBox.getChildren().add(nextBtn);
    }

    // ── UI helpers ────────────────────────────────────────────────────────────

    private VBox buildChoiceContainer(Button btn, Outcome outcome) {
        VBox container = new VBox(2);
        container.setMaxWidth(Double.MAX_VALUE);
        container.getChildren().add(btn);
        container.getChildren().add(buildSummaryBox(outcome));
        return container;
    }

    private VBox buildSummaryBox(Outcome outcome) {
        VBox box = new VBox(2);
        box.setStyle("-fx-padding: 0 0 6 10;");

        String woolong = outcome.getWoolongSummary();
        if (!woolong.isBlank()) {
            Label lbl = new Label(woolong);
            lbl.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px; " +
                    "-fx-text-fill: #f0c040; -fx-font-weight: bold;");
            box.getChildren().add(lbl);
        }

        String summary = outcome.getSummary();
        if (!summary.isBlank()) {
            for (String part : summary.split("\\s*\\|\\s*")) {
                if (!part.isBlank()) {
                    Label lbl = new Label(part);
                    lbl.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 15px; " +
                            "-fx-text-fill: #00cc33;");
                    lbl.setWrapText(true);
                    box.getChildren().add(lbl);
                }
            }
        }

        return box;
    }

    private void updateHUD() {
        var session = gameService.getSession();
        captainNameLabel.setText(session.getCaptain().getName());
        woolongLabel.setText("₩ " + session.getFinance().getWoolong());
        moraleLabel.setText("Morale: " + session.getCaptain().getMorale());
    }

    private void updateSceneVisuals(Scene scene) {
        scene.getBackgroundSprite().ifPresent(path ->
                loadImage(backgroundImage, path)
        );

        scene.getCharacterSprite().ifPresentOrElse(
                path -> {
                    loadImage(characterImage, path);
                    characterPanel.setVisible(true);
                    characterPanel.setManaged(true);
                },
                () -> {
                    characterPanel.setVisible(false);
                    characterPanel.setManaged(false);
                }
        );
    }

    private void animateText(String text) {
        if (typewriterTimeline != null) typewriterTimeline.stop();
        narrativeArea.setText("");
        final int[] index = {0};

        typewriterTimeline = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            if (index[0] < text.length()) {
                narrativeArea.appendText(String.valueOf(text.charAt(index[0]++)));
            } else {
                typewriterTimeline.stop();
            }
        }));
        typewriterTimeline.setCycleCount(text.length());
        typewriterTimeline.play();
    }

    private void startTimer(Choice choice, Scene scene, Button btn) {
        int[] remaining = {choice.getTimeoutSeconds()};
        activeTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remaining[0]--;
            btn.setText(choice.getText() + " [" + remaining[0] + "s]");
            if (remaining[0] <= 0) onChoiceSelected(scene, choice);
        }));
        activeTimer.setCycleCount(choice.getTimeoutSeconds());
        activeTimer.play();
    }

    private void stopTimer() {
        if (activeTimer != null) {
            activeTimer.stop();
            activeTimer = null;
        }
    }

    private void showEnding() {
        EndingController endingController =
                SceneManager.switchToAndGetController(AppScene.ENDING);
        endingController.initEnding(gameService.getSession());
    }

    private void showGameOver(String reason) {
        GameOverController controller =
                SceneManager.switchToAndGetController(AppScene.GAME_OVER);
        controller.initGameOver(reason, gameService.getSession());
    }

    private void loadImage(ImageView target, String path) {
        SpriteLoader.load(target, path);
    }

    private String extractMainFlag(Choice choice) {
        return choice.getOutcome().getFlagsToSet().entrySet().stream()
                .map(e -> e.getKey() + "_" + e.getValue())
                .findFirst().orElse("");
    }
}