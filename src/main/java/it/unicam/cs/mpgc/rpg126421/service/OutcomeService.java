package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.episode.Outcome;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Applica un Outcome alla sessione di gioco.
 * Responsabilità unica: tradurre le conseguenze di una scelta
 * in modifiche concrete allo stato della partita.
 */
public class OutcomeService {

    private final GameSession session;

    public OutcomeService(GameSession session) {
        this.session = session;
    }

    /**
     * Applica tutte le conseguenze dell'outcome alla sessione.
     * @return true se l'outcome causa game over
     */
    public boolean apply(Outcome outcome) {
        applyWoolong(outcome);
        applyMorale(outcome);
        applyTrust(outcome);
        applyFlags(outcome);
        return outcome.causesGameOver();
    }

    private void applyWoolong(Outcome outcome) {
        int delta = outcome.getWoolongDelta();
        if (delta > 0) {
            session.getFinance().earn(delta);
        } else if (delta < 0) {
            session.getFinance().spend(-delta);
        }
    }

    private void applyMorale(Outcome outcome) {
        session.getCaptain().changeMorale(outcome.getMoraleDelta());
    }

    private void applyTrust(Outcome outcome) {
        outcome.getTrustDeltas().forEach((name, delta) -> {
            if (name.equalsIgnoreCase("marcus")) {
                session.getMarcus().changeTrust(delta);
                updateMarcusTrustFlag();
            } else {
                session.getCrew().stream()
                        .filter(m -> m.getName().equals(name))
                        .findFirst()
                        .ifPresent(m -> m.changeTrust(delta));
            }
        });
    }

    private void applyFlags(Outcome outcome) {
        outcome.getFlagsToSet().forEach(
                (key, value) -> session.getWorldState().setFlag(key, value)
        );
    }

    private void updateMarcusTrustFlag() {
        session.getWorldState().setFlag(
                "trustMarcusHigh",
                String.valueOf(session.getMarcus().isTrustHigh())
        );
    }
}