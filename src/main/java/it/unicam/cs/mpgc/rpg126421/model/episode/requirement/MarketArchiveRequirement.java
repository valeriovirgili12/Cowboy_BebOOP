package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se il giocatore ha acquistato l'archivio Helix al mercato.
 */
public class MarketArchiveRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
        return session.getWorldState().hasFlag("marketArchive", "true");
    }

    @Override
    public String getHint() {
        return "Richiede l'Helix Archive — acquistalo al mercato nero";
    }
}