package it.unicam.cs.mpgc.rpg126421.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Gestisce la riproduzione audio del gioco.
 * Responsabilità unica: play, stop, cambio traccia.
 */
public class AudioService {

    private static final String BASE_PATH =
            "/it/unicam/cs/mpgc/rpg126421/audio/";

    public enum Track {
        MENU        ("menu_theme.mp3"),
        GAME        ("game_theme.mp3"),
        ENDING_GOOD ("ending_good.mp3"),
        GAME_OVER   ("game_over.mp3");

        private final String filename;
        Track(String filename) { this.filename = filename; }
        public String getFilename() { return filename; }
    }

    private MediaPlayer currentPlayer;

    /**
     * Avvia una traccia in loop. Ferma quella precedente.
     */
    public void play(Track track) {
        stop();
        URL resource = getClass().getResource(BASE_PATH + track.getFilename());
        if (resource == null) return; // traccia non trovata — silenzioso
        Media media = new Media(resource.toExternalForm());
        currentPlayer = new MediaPlayer(media);
        currentPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        currentPlayer.setVolume(0.5);
        currentPlayer.play();
    }

    /**
     * Ferma la traccia corrente.
     */
    public void stop() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
        }
    }

    public boolean isPlaying() { return currentPlayer != null; }
}