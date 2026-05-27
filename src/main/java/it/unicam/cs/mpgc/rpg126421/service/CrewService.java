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
     * Recluta Nyx con classe complementare al capitano.
     * Chiamato automaticamente quando il flag recruitedNyx viene settato.
     */
    public void recruitNyx() {
        if (getNyx().isPresent()) return; // già reclutata
        CrewClass nyxClass = switch (session.getCaptain().getCaptainClass()) {
            case GUNSLINGER  -> CrewClass.HACKER;
            case HACKER      -> CrewClass.GUNSLINGER;
            case BOUNTY_HUNTER, MECHANIC -> CrewClass.HACKER;
        };
        Nyx nyx = new Nyx(nyxClass);
        session.recruitCrew(nyx);
        // se Nyx è stata intimidita, parte con lealtà ridotta
        if ("true".equals(session.getWorldState().getFlag("ep2_intimidated_nyx"))) {
            nyx.setLoyal(false);
            session.getWorldState().setFlag("nyxLoyal", "false");
        }
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

    /**
     * Aggiorna la lealtà di Nyx se presente.
     */
    public void setNyxLoyal(boolean loyal) {
        getNyx().ifPresent(nyx -> nyx.setLoyal(loyal));
    }

    /**
     * Restituisce true se Nyx è nella crew ed è leale.
     */
    public boolean isNyxLoyal() {
        return getNyx().map(Nyx::isLoyal).orElse(false);
    }
}