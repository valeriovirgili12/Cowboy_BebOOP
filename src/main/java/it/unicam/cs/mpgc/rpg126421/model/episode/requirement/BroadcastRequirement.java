package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se tutti i requisiti del finale broadcast sono rispettati:
 * Lena salvata + Marcus trust alto + Nyx NON reclutata.
 */
public class BroadcastRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
        boolean lenaSaved   = session.getWorldState().hasFlag("sparedLena", "true");
        boolean marcusHigh  = session.getMarcus().isTrustHigh();
        boolean nyxAbsent   = !session.getWorldState().hasFlag("recruitedNyx", "true");
        return lenaSaved && marcusHigh && nyxAbsent;
    }

    @Override
    public String getHint() {
        return "Richiede: Lena salvata, Marcus fiducioso, Nyx non reclutata";
    }
}