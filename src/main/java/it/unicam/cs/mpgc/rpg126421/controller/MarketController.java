package it.unicam.cs.mpgc.rpg126421.controller;

import it.unicam.cs.mpgc.rpg126421.model.market.BlackMarket;
import it.unicam.cs.mpgc.rpg126421.model.market.Item;
import it.unicam.cs.mpgc.rpg126421.service.GameService;
import it.unicam.cs.mpgc.rpg126421.util.AppScene;
import it.unicam.cs.mpgc.rpg126421.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller del mercato nero.
 * Mostrato tra Episodio 1 e Episodio 2.
 */
public class MarketController {

    @FXML private Label woolongLabel;
    @FXML private VBox itemsBox;
    @FXML private Label messageLabel;
    @FXML private ImageView topItemSprite;
    @FXML private ImageView bottomItemSprite;

    private GameService gameService;
    private final BlackMarket market = new BlackMarket();

    public void initMarket(GameService gameService) {
        this.gameService = gameService;
        updateWoolong();
        renderItems();
        setupSideSprites();
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

            card.setMinHeight(210);
            card.setPrefHeight(210);

            Label name = new Label(item.getDisplayName() + " — ₩ " + item.getCost());
            name.setStyle("-fx-text-fill: #00FF41; -fx-font-family: 'Courier New';" +
                    " -fx-font-weight: bold; -fx-font-size: 20px;");

            Label desc = new Label(item.getDescription());
            desc.setStyle("-fx-text-fill: #aaaaaa; -fx-font-family: 'Courier New';" +
                    " -fx-font-size: 16px;");
            desc.setWrapText(true);

            Label effect = new Label(item.getEffect());
            effect.setStyle("-fx-text-fill: #777777; -fx-font-family: 'Courier New';" +
                    " -fx-font-size: 14px;" + " -fx-font-style: italic; -fx-wrap-text: true;");
            effect.setWrapText(true);

            Button buyBtn = new Button("ACQUISTA");
            buyBtn.getStyleClass().add("button");
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
            messageLabel.setText("Woolong insufficienti. Hai troppo cuore per essere un Cacciatore di Taglie...");
        }
    }
    private void setupSideSprites() {
        // Recuperiamo la lista di item disponibili oggi nel mercato
        var available = market.getAvailableItems(gameService.getSession());

        // Se c'è almeno un oggetto, imposta lo sprite superiore prendendolo dall'enum
        if (!available.isEmpty()) {
            Item firstItem = available.getFirst();
            topItemSprite.setImage(new Image(getClass().getResourceAsStream(firstItem.getImagePath())));
        } else {
            topItemSprite.setImage(null); // Pulisce se il mercato è vuoto
        }

        // Se ci sono almeno due oggetti, imposta lo sprite inferiore
        if (available.size() > 1) {
            Item secondItem = available.get(1);
            bottomItemSprite.setImage(new Image(getClass().getResourceAsStream(secondItem.getImagePath())));
            javafx.geometry.Insets margin = new javafx.geometry.Insets(35, 0, 0, 0); // 45px di spazio sopra
            VBox.setMargin(bottomItemSprite.getParent(), margin);
        } else {
            bottomItemSprite.setImage(null);
        }
    }

    @FXML
    private void onLeave() {
        boolean canContinue = gameService.applyFixedCosts(1000);
        if (!canContinue) {
            GameOverController gameOverController =
                    SceneManager.switchToAndGetController(AppScene.GAME_OVER);
            gameOverController.initGameOver(
                    "La Blue Mantis è rimasta senza carburante.\n" +
                            "Saldo finale: ₩ " + gameService.getSession().getFinance().getWoolong() + "\n\n" +
                            "Non arriverete mai a Europa.",
                    gameService.getSession()
            );
            return;
        }
        GameController gameController =
                SceneManager.switchToAndGetController(AppScene.GAME);
        gameController.initSession(gameService, 2); // passa indice 2 — dopo il mercato
    }
}