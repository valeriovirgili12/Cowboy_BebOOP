package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.market.BlackMarket;
import it.unicam.cs.mpgc.rpg126421.model.market.Item;
import it.unicam.cs.mpgc.rpg126421.service.GameService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller del mercato nero.
 * Mostrato tra Episodio 1 e Episodio 2.
 */
public class MarketController {

    @FXML private Label woolongLabel;
    @FXML private VBox itemsBox;
    @FXML private Label messageLabel;

    private GameService gameService;
    private final BlackMarket market = new BlackMarket();

    public void initMarket(GameService gameService) {
        this.gameService = gameService;
        updateWoolong();
        renderItems();
    }

    private void updateWoolong() {
        woolongLabel.setText("₩ " + gameService.getSession().getFinance().getWoolong());
    }

    private void renderItems() {
        itemsBox.getChildren().clear();
        var available = market.getAvailableItems(gameService.getSession());

        if (available.isEmpty()) {
            messageLabel.setText("Niente di interessante oggi.");
            return;
        }

        for (Item item : available) {
            VBox card = new VBox(4);
            card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 12; " +
                    "-fx-border-color: #333333; -fx-border-width: 1;");

            Label name = new Label(item.getDisplayName() + " — ₩ " + item.getCost());
            name.setStyle("-fx-text-fill: #f0c040; -fx-font-family: 'Courier New'; " +
                    "-fx-font-weight: bold;");

            Label desc = new Label(item.getDescription());
            desc.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: 'Courier New'; " +
                    "-fx-font-size: 11px;");
            desc.setWrapText(true);

            Label effect = new Label(item.getEffect());
            effect.setStyle("-fx-text-fill: #888888; -fx-font-family: 'Courier New'; " +
                    "-fx-font-size: 10px; -fx-font-style: italic;");
            effect.setWrapText(true);

            Button buyBtn = new Button("ACQUISTA");
            buyBtn.setStyle("-fx-background-color: #f0c040; -fx-text-fill: #0a0a0a; " +
                    "-fx-font-family: 'Courier New'; -fx-font-weight: bold; " +
                    "-fx-cursor: hand;");
            buyBtn.setOnAction(e -> onBuy(item, buyBtn));

            card.getChildren().addAll(name, desc, effect, buyBtn);
            itemsBox.getChildren().add(card);
        }
    }

    private void onBuy(Item item, Button btn) {
        boolean bought = gameService.buyItem(item);
        if (bought) {
            messageLabel.setText(item.getDisplayName() + " acquistato.");
            btn.setDisable(true);
            updateWoolong();
        } else {
            messageLabel.setText("Woolong insufficienti.");
        }
    }

    @FXML
    private void onLeave() {
        boolean canContinue = gameService.applyFixedCosts(5000);
        if (!canContinue) {
            GameOverController gameOverController =
                    SceneManager.switchToAndGetController(AppScene.GAME_OVER);
            gameOverController.initGameOver(
                    "La Blue Mantis è rimasta senza carburante.\n" +
                            "Non arriverete mai a Europa.",
                    gameService.getSession()
            );
            return;
        }
        GameController gameController =
                SceneManager.switchToAndGetController(AppScene.GAME);
        gameController.initSession(gameService);
    }
}