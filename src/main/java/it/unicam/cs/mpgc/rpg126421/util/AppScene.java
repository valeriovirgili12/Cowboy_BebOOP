package it.unicam.cs.mpgc.rpg126421.util;

/**
 * Enum di tutte le schermate del gioco.
 * Centralizza i path degli FXML — nessuna stringa sparsa nel codice.
 */
public enum AppScene {

    MAIN_MENU        ("/it/unicam/cs/mpgc/rpg126421/fxml/main-menu.fxml"),
    CHARACTER_CREATION("/it/unicam/cs/mpgc/rpg126421/fxml/character-creation.fxml"),
    GAME             ("/it/unicam/cs/mpgc/rpg126421/fxml/game.fxml"),
    ENDING           ("/it/unicam/cs/mpgc/rpg126421/fxml/ending.fxml"),
    MARKET           ("/it/unicam/cs/mpgc/rpg126421/fxml/market.fxml"),
    GAME_OVER        ("/it/unicam/cs/mpgc/rpg126421/fxml/game-over.fxml");
    private final String fxmlPath;

    AppScene(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() { return fxmlPath; }
}