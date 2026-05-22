package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.character.crew.Nyx;
import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se Nyx è nella crew ed è leale.
 */
public class NyxLoyalRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
        return session.getCrew().stream()
                .filter(m -> m instanceof Nyx)
                .map(m -> (Nyx) m)
                .findFirst()
                .map(Nyx::isLoyal)
                .orElse(false);
    }

    @Override
    public String getHint() {
        return "Richiede Nyx nella crew e leale";
    }
}