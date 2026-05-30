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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.List;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

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
    @FXML private HBox choicesBox;

    private GameService gameService;
    private int currentEpisodeIndex = 0;
    private Timeline activeTimer = null;
    private Timeline typewriterTimeline = null;


    /**
     * Chiamato da CharacterCreationController dopo il caricamento dell'FXML.
     */
    public void initSession(GameService gameService) {
        this.gameService = gameService;
        updateHUD();
        startNextEpisode();
    }
    public void initSession(GameService gameService, int episodeIndex) {
        this.gameService = gameService;
        this.currentEpisodeIndex = episodeIndex;
        updateHUD();
        startNextEpisode();
    }

    private void startTimer(Choice choice, Scene scene, Button btn) {
        int[] remaining = {choice.getTimeoutSeconds()};

        activeTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remaining[0]--;
            btn.setText(choice.getText() + " [" + remaining[0] + "s]");
            if (remaining[0] <= 0) {
                onChoiceSelected(scene, choice);
            }
        }));
        activeTimer.setCycleCount(choice.getTimeoutSeconds());
        activeTimer.play();
    }

    private void showGameOver(String reason) {
        GameOverController controller =
                SceneManager.switchToAndGetController(AppScene.GAME_OVER);
        controller.initGameOver(reason, gameService.getSession());
    }

    private void updateHUD() {
        var session = gameService.getSession();
        captainNameLabel.setText(session.getCaptain().getName());
        woolongLabel.setText("₩ " + session.getFinance().getWoolong());
        moraleLabel.setText("Morale: " + session.getCaptain().getMorale());
    }

    private void startNextEpisode() {
        // mercato nero solo tra episodio 1 e 2
        if (currentEpisodeIndex == 1) {
            currentEpisodeIndex++; // incrementa subito così non ci torna
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

    private void renderScene(Scene scene) {
        System.out.println("Scene id: " + scene.getId());

        if (isFinalScene(scene)) {
            renderFinalScene(scene);
            return;
        }
        animateText(scene.getNarrativeText());
        choicesBox.getChildren().clear();

        List<Choice> available = gameService.getAvailableChoices(scene);

        for (Choice choice : scene.getChoices()) {
            Button btn = new Button(choice.getText());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
            btn.getStyleClass().add("button");

            boolean isAvailable = available.contains(choice);
            boolean willFail    = choice.willFail(gameService.getSession());

            if (isAvailable || willFail) {
                btn.setOnAction(e -> onChoiceSelected(scene, choice));
                if (willFail) {
                    btn.setStyle("-fx-border-color: #ff4444;");
                    String hint = choice.getOutcome().hasFailureOutcome()
                            ? "⚠ Potrebbe fallire"
                            : "⚠ Requisiti non soddisfatti";
                    btn.setText(choice.getText() + "\n[" + hint + "]");
                }
                if (choice.hasTimeout()) {
                    startTimer(choice, scene, btn);
                }
            } else { btn.setDisable(true); }

            // Label conseguenze sotto il bottone
            String summary = choice.getOutcome().getSummary();
            VBox choiceContainer = new VBox(2);
            choiceContainer.setMaxWidth(Double.MAX_VALUE);
            choiceContainer.getChildren().add(btn);

            if (!summary.isBlank() || !choice.getOutcome().getWoolongSummary().isBlank()) {
                VBox summaryBox = new VBox(12);
                summaryBox.setStyle("-fx-padding: 0 0 6 10;");

                String woolongSummary = choice.getOutcome().getWoolongSummary();
                if (!woolongSummary.isBlank()) {
                    Label woolongLabel = new Label(woolongSummary);
                    woolongLabel.setStyle(
                            "-fx-font-family: 'Courier New'; " +
                                    "-fx-font-size: 15px; " +
                                    "-fx-text-fill: #f0c040; " +
                                    "-fx-font-weight: bold;"
                    );
                    summaryBox.getChildren().add(woolongLabel);
                }

                if (!summary.isBlank()) {
                    Label summaryLabel = new Label(summary);
                    summaryLabel.setStyle(
                            "-fx-font-family: 'Courier New'; " +
                                    "-fx-font-size: 15px; " +
                                    "-fx-text-fill: #00cc33;"
                    );
                    summaryBox.getChildren().add(summaryLabel);
                }

                choiceContainer.getChildren().add(summaryBox);
            }

            choicesBox.getChildren().add(choiceContainer);
        }
    }
    private void renderFinalScene(Scene scene) {
        animateText(scene.getNarrativeText());
        choicesBox.getChildren().clear();

        List<Choice> choices = scene.getChoices();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(grid, javafx.scene.layout.Priority.ALWAYS);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        int[][] positions = {{0,0}, {1,0}, {0,1}, {1,1}};

        for (int i = 0; i < choices.size() && i < 4; i++) {
            Choice choice = choices.get(i);
            boolean willFail = choice.willFail(gameService.getSession());

            VBox card = new VBox(6);
            card.setMaxWidth(Double.MAX_VALUE);
            card.setMaxHeight(Double.MAX_VALUE);
            card.setStyle(
                    "-fx-background-color: #021a02; " +
                            "-fx-border-color: " + (choice.getOutcome().hasFailureOutcome() ? "#ffb000" : "#004d14") + "; " +
                            "-fx-border-width: 1; " +
                            "-fx-padding: 12;"
            );
            GridPane.setHgrow(card, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setVgrow(card, javafx.scene.layout.Priority.ALWAYS);

            Button btn = new Button(choice.getText());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setWrapText(true);
            btn.getStyleClass().add("button");
            btn.setOnAction(e -> onChoiceSelected(scene, choice));

            if (willFail) {
                btn.setText(choice.getText() + "\n[⚠ Potrebbe fallire]");
            }

            String woolong = choice.getOutcome().getWoolongSummary();
            String summary = choice.getOutcome().getSummary();

            VBox summaryBox = new VBox(2);
            summaryBox.setStyle("-fx-padding: 4 0 0 4;");

            if (!woolong.isBlank()) {
                Label wLabel = new Label(woolong);
                wLabel.setStyle("-fx-font-family: 'Courier New'; " +
                        "-fx-font-size: 13px; -fx-text-fill: #ffb000;");
                summaryBox.getChildren().add(wLabel);
            }
            if (!summary.isBlank()) {
                Label sLabel = new Label(summary);
                sLabel.setStyle("-fx-font-family: 'Courier New'; " +
                        "-fx-font-size: 13px; -fx-text-fill: #00cc33;");
                summaryBox.getChildren().add(sLabel);
            }

            card.getChildren().addAll(btn, summaryBox);
            grid.add(card, positions[i][0], positions[i][1]);
        }

        choicesBox.getChildren().add(grid);
    }

    private void onChoiceSelected(Scene scene, Choice choice) {
        if (activeTimer != null) {
            activeTimer.stop();
            activeTimer = null;
        }
        boolean failed = choice.willFail(gameService.getSession());
        gameService.resolveScene(scene, choice);

        // testo narrativo in base a successo/fallimento
        String narrativeText = failed && choice.getOutcome().hasFailureOutcome()
                ? choice.getOutcome().getFailureOutcome().getNarrativeText()
                : choice.getOutcome().getNarrativeText();

        // reazione Marcus
        String flag = choice.getOutcome().getFlagsToSet().entrySet().stream()
                .map(e -> e.getKey() + "_" + e.getValue())
                .findFirst().orElse("");
        String marcusReaction = gameService.getSession().getMarcus().reactToChoice(flag);

        String fullText = marcusReaction.isBlank()
                ? narrativeText
                : narrativeText + "\n\nMarcus: " + marcusReaction;

        animateText(fullText);

        choicesBox.getChildren().clear();
        updateHUD();

        if (gameService.isGameOver()) {
            showGameOver(narrativeText);
            return;
        }

        Button nextBtn = new Button("[ CONTINUA → ]"); // Racchiuderlo tra quadre aiuta lo stile!
        nextBtn.setOnAction(e -> advance());
        nextBtn.getStyleClass().add("button");
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
    private boolean isFinalScene(Scene scene) {
        return scene.getId().equals("ep3_s3");
    }

    private void animateText(String text) {
        // Se c'era un'animazione precedente ancora in corso, la stoppiamo
        if (typewriterTimeline != null) {
            typewriterTimeline.stop();
        }

        // Svuotiamo l'area prima di iniziare
        narrativeArea.setText("");

        // Usiamo un array a elemento singolo per poter modificare l'indice dentro la Lambda
        final int[] index = {0};

        typewriterTimeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(40), event -> {
            if (index[0] < text.length()) {
                narrativeArea.appendText(String.valueOf(text.charAt(index[0])));
                index[0]++;
            } else {
                typewriterTimeline.stop();
            }
        });

        typewriterTimeline.getKeyFrames().add(keyFrame);
        typewriterTimeline.setCycleCount(text.length());
        typewriterTimeline.play();
    }
}