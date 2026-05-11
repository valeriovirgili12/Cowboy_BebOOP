package it.unicam.cs.mpgc.rpg126421.model.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Tiene traccia dei flag globali di gioco.
 * Ogni scelta significativa del giocatore lascia un segno qui.
 * Esempio: "ep1_saved_hostage" -> "true", "reputation" -> "opportunist"
 */
public class WorldState {

    private final Map<String, String> flags;

    public WorldState() {
        this.flags = new HashMap<>();
    }

    /**
     * Imposta o sovrascrive un flag.
     */
    public void setFlag(String key, String value) {
        if (key == null || key.isBlank()) throw new IllegalArgumentException("Flag key cannot be blank");
        flags.put(key, value);
    }

    /**
     * Restituisce il valore di un flag, o null se non esiste.
     */
    public String getFlag(String key) {
        return flags.get(key);
    }

    /**
     * Controlla se un flag ha esattamente quel valore.
     */
    public boolean hasFlag(String key, String value) {
        return value.equals(flags.get(key));
    }

    /**
     * Controlla se un flag esiste (qualunque valore).
     */
    public boolean hasFlag(String key) {
        return flags.containsKey(key);
    }

    @Override
    public String toString() {
        return "WorldState" + flags;
    }
}