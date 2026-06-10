package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se il giocatore ha ottenuto la password di Lena.
 */
public class LenaPasswordRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
        return session.getWorldState().hasFlag("lenaPassword", "true");
    }

    @Override
    public String getHint() {
        return "Richiede la password di Lena — salvala o copiale i dati";
    }
}