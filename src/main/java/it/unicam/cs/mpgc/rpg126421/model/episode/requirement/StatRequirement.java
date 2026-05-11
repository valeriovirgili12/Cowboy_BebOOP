package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.Stat;

/**
 * Soddisfatta se la somma di una stat tra tutti i crew member supera la soglia.
 */
public class StatRequirement implements Requirement {

    private final Stat stat;
    private final int threshold;

    public StatRequirement(Stat stat, int threshold) {
        this.stat      = stat;
        this.threshold = threshold;
    }

    @Override
    public boolean isMet(GameSession session) {
        int total = session.getCrew().stream()
                .mapToInt(m -> switch (stat) {
                    case STRENGTH  -> m.getCharacterClass().getStrength();
                    case TECH      -> m.getCharacterClass().getTech();
                    case RESILIENCE-> m.getCharacterClass().getResilience();
                })
                .sum();
        return total >= threshold;
    }

    @Override
    public String getHint() {
        return "Serve almeno " + threshold + " punti " + stat.name() + " nella crew";
    }
}