package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterRole;

/**
 * Soddisfatta se il capitano o un membro della crew
 * ha il displayName della classe richiesta.
 * Funziona sia con CaptainClass che con CrewClass.
 */
public class CrewClassRequirement implements Requirement {

    private final String requiredClassName;

    public CrewClassRequirement(CharacterRole required) {
        this.requiredClassName = required.getDisplayName();
    }

    @Override
    public boolean isMet(GameSession session) {
        // controlla il capitano
        boolean captainMatches = session.getCaptain()
                .getCharacterRole()
                .getDisplayName()
                .equals(requiredClassName);

        // controlla la crew
        boolean crewMatches = session.getCrew().stream()
                .anyMatch(m -> m.getCharacterRole()
                        .getDisplayName()
                        .equals(requiredClassName));

        return captainMatches || crewMatches;
    }

    @Override
    public String getHint() {
        return "Richiede un " + requiredClassName + " nel team";
    }
}