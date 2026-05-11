package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

/**
 * Condizione necessaria per rendere una scelta disponibile al giocatore.
 * Implementazioni concrete controllano crew, stat, woolong o flag.
 */
public interface Requirement {

    /**
     * Restituisce true se la condizione è soddisfatta.
     */
    boolean isMet(GameSession session);

    /**
     * Testo mostrato al giocatore quando la scelta non è disponibile.
     * Esempio: "Serve un Hacker nella crew"
     */
    String getHint();
}