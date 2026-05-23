package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se Marcus ha un livello di fiducia alto.
 */
public class MarcusTrustRequirement implements Requirement {

    @Override
    public boolean isMet(GameSession session) {
        return session.getMarcus().isTrustHigh();
    }

    @Override
    public String getHint() {
        return "Richiede la fiducia di Marcus — le tue scelte lo hanno deluso";
    }
}
