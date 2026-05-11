package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se WorldState contiene un flag con un determinato valore.
 */
public class FlagRequirement implements Requirement {

    private final String key;
    private final String expectedValue;

    public FlagRequirement(String key, String expectedValue) {
        this.key           = key;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean isMet(GameSession session) {
        return session.getWorldState().hasFlag(key, expectedValue);
    }

    @Override
    public String getHint() {
        return "Richiede una scelta precedente: " + key + " = " + expectedValue;
    }
}