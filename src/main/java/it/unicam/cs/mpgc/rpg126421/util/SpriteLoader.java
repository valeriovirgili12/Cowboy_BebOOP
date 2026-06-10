package it.unicam.cs.mpgc.rpg126421.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Utility per il caricamento sicuro di sprite dal classpath.
 */
public class SpriteLoader {

    private SpriteLoader() {}

    public static void load(ImageView target, String path) {
        if (path == null) return;
        var stream = SpriteLoader.class.getResourceAsStream(path);
        if (stream == null) {
            System.err.println("Sprite non trovato: " + path);
            return;
        }
        target.setImage(new Image(stream));
    }
}