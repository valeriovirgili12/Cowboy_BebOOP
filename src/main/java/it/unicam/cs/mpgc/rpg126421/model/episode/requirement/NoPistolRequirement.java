package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se il giocatore ha acquistato la pistola Raven.
 */
public class PistolRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
    System.out.println("Flag pistola"+ session.getWorldState().getFlag("pistol"));
    return session.getWorldState().hasFlag("pistol", "false");
    }

    @Override
    public String getHint() {
        return "Richiede la pistola Raven — acquistala al mercato nero";
    }
}