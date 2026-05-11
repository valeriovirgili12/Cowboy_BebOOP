package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Soddisfatta se il giocatore ha abbastanza Woolong.
 */
public class WoolongRequirement implements Requirement {

    private final int amount;

    public WoolongRequirement(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean isMet(GameSession session) {
        return session.getFinance().canAfford(amount);
    }

    @Override
    public String getHint() {
        return "Servono almeno " + amount + " Woolong";
    }
}