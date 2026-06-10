package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.character.crew.Nyx;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

import java.util.Optional;

/**
 * Gestisce la crew della Blue Mantis.
 * Responsabilità unica: reclutamento, licenziamento, stato crew.
 */
public class CrewService {

    private final GameSession session;

    public CrewService(GameSession session) {
        this.session = session;
    }

    /**
     * Recluta Nyx con classe HACKER.
     * Chiamato automaticamente quando il flag recruitedNyx viene settato.
     */
    public void recruitNyx() {
        if (getNyx().isPresent()) return; // già reclutata
        CrewClass nyxClass = CrewClass.HACKER;

    /*Funzionalità non implementata, Nyx viene reclutata con classe complementare al capitano*/

        /*switch (session.getCaptain().getCaptainClass()) {
            case GUNSLINGER  -> CrewClass.HACKER;
            case HACKER      -> CrewClass.GUNSLINGER;
            case BOUNTY_HUNTER, MECHANIC -> CrewClass.HACKER;
        };*/
        Nyx nyx = new Nyx(nyxClass);
        session.recruitCrew(nyx);
    }

    /**
     * Restituisce Nyx se presente nella crew.
     */
    public Optional<Nyx> getNyx() {
        return session.getCrew().stream()
                .filter(m -> m instanceof Nyx)
                .map(m -> (Nyx) m)
                .findFirst();
    }

}